package ${packagePath}.model;

<#if entity.hasCompoundPK()>
	import ${packagePath}.service.persistence.${entity.name}PK;
</#if>

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class is used by SOAP remote services<#if entity.hasRemoteService()>, specifically {@link ${packagePath}.service.http.${entity.name}ServiceSoap}</#if>.
 *
 * @author ${author}
<#if entity.hasRemoteService()>
 * @see ${packagePath}.service.http.${entity.name}ServiceSoap
</#if>
 * @generated
 */
@ProviderType
public class ${entity.name}Soap implements Serializable {

	public static ${entity.name}Soap toSoapModel(${entity.name} model) {
		${entity.name}Soap soapModel = new ${entity.name}Soap();

		<#list entity.regularColList as column>
			soapModel.set${column.methodName}(model.get${column.methodName}());
		</#list>

		return soapModel;
	}

	public static ${entity.name}Soap[] toSoapModels(${entity.name}[] models) {
		${entity.name}Soap[] soapModels = new ${entity.name}Soap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ${entity.name}Soap[][] toSoapModels(${entity.name}[][] models) {
		${entity.name}Soap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ${entity.name}Soap[models.length][models[0].length];
		}
		else {
			soapModels = new ${entity.name}Soap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ${entity.name}Soap[] toSoapModels(List<${entity.name}> models) {
		List<${entity.name}Soap> soapModels = new ArrayList<${entity.name}Soap>(models.size());

		for (${entity.name} model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ${entity.name}Soap[soapModels.size()]);
	}

	public ${entity.name}Soap() {
	}

	public ${entity.PKClassName} getPrimaryKey() {
		<#if entity.hasCompoundPK()>
			return new ${entity.PKClassName}(
				<#list entity.PKList as column>
					_${column.name}

					<#if column_has_next>
						,
					</#if>
				</#list>
			);
		<#else>
			return _${entity.PKList[0].name};
		</#if>
	}

	public void setPrimaryKey(${entity.PKClassName} pk) {
		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				set${column.methodName}(pk.${column.name});
			</#list>
		<#else>
			set${entity.PKList[0].methodName}(pk);
		</#if>
	}

	<#list entity.regularColList as column>
		public ${column.genericizedType} get${column.methodName}() {
			return _${column.name};
		}

		<#if column.type== "boolean">
			public ${column.type} is${column.methodName}() {
				return _${column.name};
			}
		</#if>

		public void set${column.methodName}(${column.genericizedType} ${column.name}) {
			_${column.name} = ${column.name};
		}
	</#list>

	<#list entity.regularColList as column>
		private ${column.genericizedType} _${column.name};
	</#list>

}