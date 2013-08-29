require 'compass'
require 'fileutils'
require 'java'
require 'rubygems'
require 'sass/plugin'

java_import com.liferay.portal.kernel.log.LogFactoryUtil
java_import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSFilter

log = LogFactoryUtil.getLog(DynamicCSSFilter.java_class)

if log.isDebugEnabled
	Sass.logger.log_level = :debug
else
	Sass.logger.log_level = :error
end

Compass.add_project_configuration

Compass.configuration.project_path ||= $cssThemePath

load_paths = []

if $cssThemePath
	load_paths += [$cssThemePath]
end

load_paths += Compass.configuration.sass_load_paths

engine = Sass::Engine.new(
	$content,
	{
		:cache_location => $sassCachePath,
		:debug_info => log.isDebugEnabled,
		:filename => $cssRealPath,
		:full_exception => log.isDebugEnabled,
		:line => 0,
		:load_paths => load_paths,
		:syntax => :scss,
		:ugly => true
	}
)

begin
	$out.println engine.render
rescue Sass::SyntaxError => e
	puts e.message
	puts e.backtrace
end