require 'json'
require 'cgi'
require 'typhoeus'

# Executes HTTP requests against Testray and returns a JSON parsed response
def testray_req(request_method, endpoint, params = {}, body = {})
    request = Typhoeus::Request.new(@testray_path + endpoint,
                                    method: request_method,
                                    body: body,
                                    params: params,
                                    headers: {Accept: 'application/json'},
                                    userpwd: "#{@username}:#{@password}",
                                    ssl_verifypeer: false,
                                    verbose: false)

    request.on_complete do |response|
        response_code_string = response.code.to_s

        if !response_code_string.start_with?('2')
            raise "Received Status #{request.response.code}: #{request.response.status_message}. Response body: #{request.response.body}"
        elsif response.timed_out?
            raise 'Request timed out.'
        end
    end

    request.run

    return JSON.parse(request.response.body)
end

# Get and return Product Version ID of a Product Version given by name
def get_product_ver_id(project_id = @project_id, product_ver = @product_ver)
    response_json = testray_req(:get, '/product_versions.json', {testrayProjectId: project_id})

    begin
        product_ver_id = response_json['data'].find {|x| x['name'] == "#{product_ver}"}['testrayProductVersionId']
    rescue
        raise "Unable to store testrayProductVersionId because '#{product_ver}' was not found in the response."
    end

    return product_ver_id
end

# Creates a Build and returns the Build ID
def create_build(routine_id = @routine_id, build_name = @build_name, product_ver_id = @product_ver_id)
    req_data = {
          testrayRoutineId: "#{routine_id}",
          description: "[Build ##{ENV['TRAVIS_BUILD_NUMBER']}] #{ENV['TRAVIS_COMMIT_MESSAGE']}\n
                        https://github.com/liferay/com-liferay-osb-faro-private/pull/#{ENV['TRAVIS_PULL_REQUEST']}",
          testrayProductVersionId: "#{product_ver_id}",
          name: "#{build_name}"
    }

    begin
        response_json = testray_req(:post, '/builds.json', {}, req_data)
    rescue => e
        if e.message.include?('The build name already exists')
            response_json = testray_req(:get, "/builds/_#{CGI::escape(build_name)}.json", {testrayRoutineId: routine_id})

            @build_exists_flag = true
        end
    end

    return response_json['data']['testrayBuildId']
end

# Gets the Default Factors map for a Routine by ID
def get_default_factors(routine_id = @routine_id)
    response_json = testray_req(:get, '/routines/view.json', {id: routine_id})

    return response_json['data']['defaultTestrayFactors']
end

# Creates a Run and returns the Run ID
def create_run(build_id = @build_id, default_factors = @default_factors)
    request_data = {
          testrayBuildId: "#{build_id}",
          testrayFactors: "#{default_factors}"
    }

    if !@build_exists_flag
        response_json = testray_req(:post, '/runs.json', {}, request_data)

        return response_json['data']['testrayRunId']
    else
        response_json = testray_req(:get, '/runs.json', {testrayBuildId: build_id})

        return response_json['data'][0]['testrayRunId']
    end

end

# Import all Cucumber Results from a directory
def import_results_dir(directory_path = @results_dir, run_id = @run_id, result_type: 'cucumber')
    failed_imports = {} # file name => failure message
    result_files = File.join(File.dirname(__FILE__), directory_path, '/*.json')

    Dir.glob(result_files) do |json_file|
        results = File.open(json_file, mode: 'rb')

        puts "Importing #{json_file} into Run with ID: #{run_id}"

        if results.read[0] == '['
            results.rewind

            @result_array = JSON::parse(results.read)

            @result_array.each do |results_blob|
                begin
                    testray_req(:post, '/case_results/importResults.json', {},
                                {type: result_type, testrayRunId: run_id, results: results_blob.to_json})
                rescue => e
                    failed_imports[results_blob['uri']] = "#{e.message}"
                end
            end
        elsif results.read[0] == '{'
            results.rewind

            begin
                testray_req(:post, '/case_results/importResults.json', {},
                            {type: result_type, testrayRunId: run_id, results: JSON::parse(results.read).to_json})
            rescue => e
                failed_imports[json_file] = "#{e.message}"
            end
        else
            results.rewind

            first_char = results.read[0]
            raise "Unknown first char '#{first_char}' in file #{json_file}"
        end

        results.close
    end

    (defined?(@result_array) != nil) ? n_results = @result_array.size : n_results = Dir.glob(directory_path).size

    if failed_imports.size == 0
        puts "All #{n_results} #{result_type} results were successfully imported from directory: '#{directory_path}'"
    else
        puts "#{failed_imports.size} of #{n_results} failed to import. The following results failed to be imported:\n"

        failed_imports.each_key do |key|
            puts "Result Filename: #{key}\t---\t#{failed_imports[key]}\n"
        end
    end
end