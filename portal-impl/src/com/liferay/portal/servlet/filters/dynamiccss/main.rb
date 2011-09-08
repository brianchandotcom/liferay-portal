require 'java'
require 'rubygems'
require 'fileutils'
require 'compass'

java_import com.liferay.portal.kernel.log.LogFactoryUtil
java_import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSFilter

log = LogFactoryUtil.getLog(DynamicCSSFilter.java_class)

Compass.add_project_configuration
Compass.configuration.project_path ||= $cssThemePath

load_paths = []

load_paths += Compass.configuration.sass_load_paths

engine = Sass::Engine.new(
	$content,
	{
		:cache_location => $sassCachePath,
		:debug_info => log.isDebugEnabled,
		:filename => $cssRealPath,
		:full_exception => log.isDebugEnabled,
		:syntax => :scss,
		:load_paths => load_paths,
		:ugly => true
	}
)

$out.println engine.render