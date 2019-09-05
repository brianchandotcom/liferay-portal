package ${apiPackagePath}.model;

<#if entity.hasCompoundPK()>
	import ${apiPackagePath}.service.persistence.${entity.name}PK;
</#if>

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the ${column.name} column in ${entity.name}.
 *
 * @author ${author}
 * @see ${entity.name}
 * @generated
 */
public class ${entity.name}${column.methodName}BlobModel {

	public ${entity.name}${column.methodName}BlobModel() {
	}

	<#if entity.hasCompoundPK()>
		<#assign
			pkEntityMethodName = entity.PKClassName
			pkEntityType = entity.PKClassName
			pkEntityVarName = entity.PKVarName
		/>

	<#else>
		<#assign
			pkEntityColumn = entity.PKEntityColumns?first

			pkEntityMethodName = pkEntityColumn.methodName
			pkEntityType = pkEntityColumn.type
			pkEntityVarName = pkEntityColumn.name
		/>
	</#if>

	public ${entity.name}${column.methodName}BlobModel(
		${pkEntityType} ${pkEntityVarName}) {

		_${pkEntityVarName} = ${pkEntityVarName};
	}

	public ${entity.name}${column.methodName}BlobModel(
		${pkEntityType} ${pkEntityVarName}, Blob ${column.name}Blob) {

		_${pkEntityVarName} = ${pkEntityVarName};
		_${column.name}Blob = ${column.name}Blob;
	}

	public ${pkEntityType} get${pkEntityMethodName}() {
		return _${pkEntityVarName};
	}

	public void set${pkEntityMethodName}(${pkEntityType} ${pkEntityVarName}) {
		_${pkEntityVarName} = ${pkEntityVarName};
	}

	public Blob get${column.methodName}Blob() {
		return _${column.name}Blob;
	}

	public void set${column.methodName}Blob(Blob ${column.name}Blob) {
		_${column.name}Blob = ${column.name}Blob;
	}

	private ${pkEntityType} _${pkEntityVarName};

	private Blob _${column.name}Blob;

}