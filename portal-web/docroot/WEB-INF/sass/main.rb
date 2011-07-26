require 'java'
require 'rubygems'
require 'sass'

java_import com.liferay.portal.kernel.log.LogFactoryUtil
java_import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSFilter

log = LogFactoryUtil.getLog(DynamicCSSFilter.java_class)

engine = Sass::Engine.new(
	$content,
	{
		:debug_info => log.isDebugEnabled,
		:filename => $cssRealPath,
		:full_exception => log.isDebugEnabled,
		:syntax => :scss,
		:load_paths => ['html/'],
		:ugly => true
	}
)

$out.println engine.render