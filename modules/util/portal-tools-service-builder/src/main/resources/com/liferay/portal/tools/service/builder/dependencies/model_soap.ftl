package ${apiPackagePath}.model;

<#if entity.hasCompoundPK()>
	import ${apiPackagePath}.service.persistence.${entity.name}PK;
</#if>

import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This class is used by SOAP remote services<#if entity.hasRemoteService()>, specifically {@link ${packagePath}.service.http.${entity.name}ServiceSoap}</#if>.
 *
 * @author ${author}
<#if serviceBuilder.isVersionGTE_7_3_0()>
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
<#elseif classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if serviceBuilder.isVersionGTE_7_3_0() || classDeprecated>
	@Deprecated
</#if>
public class ${entity.name}Soap implements Serializable {

	public static ${entity.name}Soap toSoapModel(${entity.name} model) {
		${entity.name}Soap soapModel = new ${entity.name}Soap();

		<#list entity.regularEntityColumns as entityColumn>
			<#if stringUtil.equals(entityColumn.type, "boolean")>
				soapModel.set${entityColumn.methodName}(model.is${entityColumn.methodName}());
			<#else>
				soapModel.set${entityColumn.methodName}(model.get${entityColumn.methodName}());
			</#if>
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
				<#list entity.PKEntityColumns as entityColumn>
					_${entityColumn.name}

					<#if entityColumn_has_next>
						,
					</#if>
				</#list>
			);
		<#else>
			return _${entity.PKEntityColumns[0].name};
		</#if>
	}

	public void setPrimaryKey(${entity.PKClassName} pk) {
		<#if entity.hasCompoundPK()>
			<#list entity.PKEntityColumns as entityColumn>
				set${entityColumn.methodName}(pk.${entityColumn.name});
			</#list>
		<#else>
			set${entity.PKEntityColumns[0].methodName}(pk);
		</#if>
	}

	<#list entity.regularEntityColumns as entityColumn>
		public ${entityColumn.genericizedType} get${entityColumn.methodName}() {
			return _${entityColumn.name};
		}

		<#if entityColumn.type== "boolean">
			public ${entityColumn.type} is${entityColumn.methodName}() {
				return _${entityColumn.name};
			}
		</#if>

		public void set${entityColumn.methodName}(${entityColumn.genericizedType} ${entityColumn.name}) {
			_${entityColumn.name} = ${entityColumn.name};
		}
	</#list>

	<#list entity.regularEntityColumns as entityColumn>
		private ${entityColumn.genericizedType} _${entityColumn.name};
	</#list>

}