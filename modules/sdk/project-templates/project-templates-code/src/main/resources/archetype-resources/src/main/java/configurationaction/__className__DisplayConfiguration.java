package ${package}.${template};

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author ${author}
 */
@Meta.OCD(
	id = "${package}.${template}.${className}DisplayConfiguration"
)
public interface ${className}DisplayConfiguration {

	@Meta.AD(required = false)
	public String fontColor();

	@Meta.AD(required = false)
	public String fontFamily();

	@Meta.AD(required = false)
	public int fontSize();

}