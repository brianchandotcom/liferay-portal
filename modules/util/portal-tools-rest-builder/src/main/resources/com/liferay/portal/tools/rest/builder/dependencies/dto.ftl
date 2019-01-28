package ${configYAML.apiPackagePath}.dto;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@XmlRootElement(name = "${schemaName}")
public class ${schemaName} {

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	private long _id;

}