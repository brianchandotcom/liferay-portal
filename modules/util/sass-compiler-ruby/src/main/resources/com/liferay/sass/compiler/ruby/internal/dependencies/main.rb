require 'compass'
require 'pathname'
require 'sass/plugin'

class SASSWrapper
	def initialize()
		Compass.add_project_configuration

		@load_paths = Compass.configuration.sass_load_paths
	end

	def process(inputFileName, includePath, sassCachePath, debug=false, outputFileName, precision, generateSourceMap, sourceMapFileName)
		Sass::Script::Number.precision = precision

		load_paths = includePath.split(File::PATH_SEPARATOR)

		load_paths += @load_paths

		if generateSourceMap
			inputFilePath = Pathname.new(inputFileName)

			basePath = inputFilePath.parent

			sourceMapFilePath = Pathname.new(sourceMapFileName)

			engine = Sass::Engine.for_file(
				inputFileName,
				{
					:cache_location => sassCachePath,
					:debug_info => debug,
					:full_exception => debug,
					:line => 0,
					:load_paths => load_paths,
					:sourcemap => :file,
					:syntax => :scss
				})

			result = engine.render_with_sourcemap(sourceMapFilePath.relative_path_from(basePath).to_s)

			css = result[0]

			sourceMap = result[1].to_json(
				{
					:css_path => outputFileName,
					:sourcemap_path => sourceMapFileName
				})
			
			return css, sourceMap
		else
			engine = Sass::Engine.for_file(
				inputFileName,
				{
					:cache_location => sassCachePath,
					:debug_info => debug,
					:full_exception => debug,
					:line => 0,
					:load_paths => load_paths,
					:syntax => :scss
				})

			return engine.render, ""
		end
	end
end

SASSWrapper.new()