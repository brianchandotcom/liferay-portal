package ${packagePath}.model;

import aQute.bnd.annotation.ProviderType;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the ${column.name} column in ${entity.name}.
 *
 * @author ${author}
 * @see ${entity.name}
 * @generated
 */
@ProviderType
public class ${entity.name}${column.methodName}BlobModel {

	public ${entity.name}${column.methodName}BlobModel() {
	}

	<#assign pkColumn = entity.getPKList()?first>

	public ${entity.name}${column.methodName}BlobModel(
		${pkColumn.type} ${pkColumn.name}) {

		_${pkColumn.name} = ${pkColumn.name};
	}

	public ${entity.name}${column.methodName}BlobModel(
		${pkColumn.type} ${pkColumn.name}, Blob ${column.name}Blob) {

		_${pkColumn.name} = ${pkColumn.name};
		_${column.name}Blob = ${column.name}Blob;
	}

	public ${entity.PKClassName} get${pkColumn.methodName}() {
		return _${entity.PKVarName};
	}

	public void set${pkColumn.methodName}(${entity.PKClassName} ${entity.PKVarName}) {
		_${entity.PKVarName} = ${entity.PKVarName};
	}

	public Blob get${column.methodName}Blob() {
		return _${column.name}Blob;
	}

	public void set${column.methodName}Blob(Blob ${column.name}Blob) {
		_${column.name}Blob = ${column.name}Blob;
	}

	<#if entity.hasCompoundPK()>
		private ${entity.PKClassName} _${entity.PKVarName};
	<#else>
		private ${pkColumn.type} _${pkColumn.name};
	</#if>

	private Blob _${column.name}Blob;

}