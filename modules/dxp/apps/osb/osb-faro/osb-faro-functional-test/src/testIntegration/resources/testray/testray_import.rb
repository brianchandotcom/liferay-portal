require_relative './import_helpers.rb'

# ===== Import Parameters =====
@testray_env = 'testray.liferay.com'
@project_id = '275316372'
@routine_id = '304645635'
@product_ver = 'Milestone 1'
@results_dir = '../../../../build/reports/json'

@username = 'sir.testalot@liferay.com'
@password = 'TestyourmighT'

@testray_path = "https://#{@testray_env}/web/guest/home/-/testray"

if ENV['TRAVIS_PULL_REQUEST'] != 'false'
    @build_name = "PR ##{ENV['TRAVIS_PULL_REQUEST']} - #{ENV['TRAVIS_PULL_REQUEST_BRANCH']} > " +
        "#{ENV['TRAVIS_BRANCH']} - #{ENV['TRAVIS_COMMIT']}"
else
    @build_name = "[Build ##{ENV['TRAVIS_BUILD_NUMBER']}] #{ENV['TRAVIS_BRANCH']} - #{ENV['TRAVIS_COMMIT']}"
end

# ===== Import Cucumber JSON Results =====
@product_ver_id = get_product_ver_id
@build_id = create_build

@default_factors = get_default_factors
@run_id = create_run

import_results_dir

puts 'testray_import.rb has finished executing.'
puts "Results are at: #{@testray_path}/case_results?testrayBuildId=#{@build_id}&testrayRunId=#{@run_id}"
