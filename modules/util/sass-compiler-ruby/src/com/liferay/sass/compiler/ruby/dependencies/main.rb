require 'compass'
require 'sass/plugin'

class SASSWrapper
	def initialize()
		Compass.add_project_configuration

		@load_paths = Compass.configuration.sass_load_paths
	end

	def process(content, commonSassPath, cssRootPath, sassCachePath, debug=false)
		Compass.configuration.project_path ||= cssRootPath

		load_paths = [commonSassPath, cssRootPath]
		load_paths += @load_paths

		engine = Sass::Engine.new(
			content,
			{
				:cache_location => sassCachePath,
				:debug_info => debug,
				:full_exception => debug,
				:line => 0,
				:load_paths => load_paths,
				:syntax => :scss,
				:ugly => true
			}
		)

		engine.render
	end
end

SASSWrapper.new()