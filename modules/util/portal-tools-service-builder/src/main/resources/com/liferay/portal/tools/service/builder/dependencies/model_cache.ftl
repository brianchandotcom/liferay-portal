package ${packagePath}.model.impl;

import ${apiPackagePath}.model.${entity.name};

<#if entity.hasCompoundPK()>
	import ${apiPackagePath}.service.persistence.${entity.name}PK;
</#if>

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;

/**
 * The cache model class for representing ${entity.name} in entity cache.
 *
 * @author ${author}
 * @see ${entity.name}
<#if classDeprecated>
 * @deprecated ${classDeprecatedComment}
</#if>
 * @generated
 */

<#if classDeprecated>
	@Deprecated
</#if>

@ProviderType
public class ${entity.name}CacheModel implements CacheModel<${entity.name}>, Externalizable
	<#if entity.isMvccEnabled()>
		, MVCCModel
	</#if>

	{

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ${entity.name}CacheModel)) {
			return false;
		}

		${entity.name}CacheModel ${entity.varName}CacheModel = (${entity.name}CacheModel)obj;

		<#if entity.hasPrimitivePK(false)>
			<#if entity.isMvccEnabled()>
				if ((${entity.PKVarName} == ${entity.varName}CacheModel.${entity.PKVarName}) && (mvccVersion == ${entity.varName}CacheModel.mvccVersion)) {
			<#else>
				if (${entity.PKVarName} == ${entity.varName}CacheModel.${entity.PKVarName}) {
			</#if>
		<#else>
			<#if entity.isMvccEnabled()>
				if (${entity.PKVarName}.equals(${entity.varName}CacheModel.${entity.PKVarName}) && (mvccVersion == ${entity.varName}CacheModel.mvccVersion)) {
			<#else>
				if (${entity.PKVarName}.equals(${entity.varName}CacheModel.${entity.PKVarName})) {
			</#if>
		</#if>

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		<#if entity.isMvccEnabled()>
			int hashCode = HashUtil.hash(0, ${entity.PKVarName});

			return HashUtil.hash(hashCode, mvccVersion);
		<#else>
			return HashUtil.hash(0, ${entity.PKVarName});
		</#if>
	}

	<#if entity.isMvccEnabled()>
		@Override
		public long getMvccVersion() {
			return mvccVersion;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
			this.mvccVersion = mvccVersion;
		}
	</#if>

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(${(entity.regularEntityColumns?size - entity.blobEntityColumns?size) * 2 + 1});

		<#list entity.regularEntityColumns as entityColumn>
			<#if !stringUtil.equals(entityColumn.type, "Blob")>
				<#if entityColumn_index == 0>
					sb.append("{${entityColumn.name}=");
					sb.append(${entityColumn.name});
				<#elseif entityColumn_has_next>
					sb.append(", ${entityColumn.name}=");
					sb.append(${entityColumn.name});
				<#else>
					sb.append(", ${entityColumn.name}=");
					sb.append(${entityColumn.name});
					sb.append("}");
				</#if>
			</#if>
		</#list>

		return sb.toString();
	}

	@Override
	public ${entity.name} toEntityModel() {
		${entity.name}Impl ${entity.varName}Impl = new ${entity.name}Impl();

		<#list entity.regularEntityColumns as entityColumn>
			<#if !stringUtil.equals(entityColumn.type, "Blob")>
				<#if stringUtil.equals(entityColumn.type, "Date")>
					if (${entityColumn.name} == Long.MIN_VALUE) {
						${entity.varName}Impl.set${entityColumn.methodName}(null);
					}
					else {
						${entity.varName}Impl.set${entityColumn.methodName}(new Date(${entityColumn.name}));
					}
				<#else>
					<#if stringUtil.equals(entityColumn.type, "String")>
						if (${entityColumn.name} == null) {
							${entity.varName}Impl.set${entityColumn.methodName}("");
						}
						else {
							${entity.varName}Impl.set${entityColumn.methodName}(${entityColumn.name});
						}
					<#else>
						${entity.varName}Impl.set${entityColumn.methodName}(${entityColumn.name});
					</#if>
				</#if>
			</#if>
		</#list>

		${entity.varName}Impl.resetOriginalValues();

		<#list cacheFields as cacheField>
			<#assign methodName = serviceBuilder.getCacheFieldMethodName(cacheField) />

			${entity.varName}Impl.set${methodName}(${cacheField.name});
		</#list>

		return ${entity.varName}Impl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws
		<#assign throwsClassNotFoundException = false />

		<#list entity.regularEntityColumns as entityColumn>
			<#if entityColumn.primitiveType>
			<#elseif stringUtil.equals(entityColumn.type, "Date")>
			<#elseif stringUtil.equals(entityColumn.type, "String")>
			<#elseif !stringUtil.equals(entityColumn.type, "Blob")>
				<#assign throwsClassNotFoundException = true />
			</#if>
		</#list>

		<#if (cacheFields?size > 0)>
			<#assign throwsClassNotFoundException = true />
		</#if>

		<#if throwsClassNotFoundException>
			ClassNotFoundException,
		</#if>

		IOException {

		<#list entity.regularEntityColumns as entityColumn>
			<#if entityColumn.primitiveType>
				<#assign entityColumnPrimitiveType = serviceBuilder.getPrimitiveType(entityColumn.genericizedType) />

				${entityColumn.name} = objectInput.read${textFormatter.format(entityColumnPrimitiveType, 6)}();
			<#elseif stringUtil.equals(entityColumn.type, "Date")>
				${entityColumn.name} = objectInput.readLong();
			<#elseif stringUtil.equals(entityColumn.type, "String")>
				${entityColumn.name} = objectInput.readUTF();
			<#elseif !stringUtil.equals(entityColumn.type, "Blob")>
				${entityColumn.name} = (${entityColumn.genericizedType})objectInput.readObject();
			</#if>
		</#list>

		<#list cacheFields as cacheField>
			${cacheField.name} = (${serviceBuilder.getGenericValue(cacheField.type)})objectInput.readObject();
		</#list>

		<#if entity.hasCompoundPK()>
			${entity.PKVarName} = new ${entity.PKClassName}(

				<#list entity.PKEntityColumns as entityColumn>
					${entityColumn.name}

					<#if entityColumn_has_next>
						,
					</#if>
				</#list>

				);
		</#if>
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		<#list entity.regularEntityColumns as entityColumn>
			<#if entityColumn.primitiveType>
				<#assign entityColumnPrimitiveType = serviceBuilder.getPrimitiveType(entityColumn.genericizedType) />

				objectOutput.write${textFormatter.format(entityColumnPrimitiveType, 6)}(${entityColumn.name});
			<#elseif stringUtil.equals(entityColumn.type, "Date")>
				objectOutput.writeLong(${entityColumn.name});
			<#elseif stringUtil.equals(entityColumn.type, "String")>
				if (${entityColumn.name} == null) {
					objectOutput.writeUTF("");
				}
				else {
					objectOutput.writeUTF(${entityColumn.name});
				}
			<#elseif !stringUtil.equals(entityColumn.type, "Blob")>
				objectOutput.writeObject(${entityColumn.name});
			</#if>
		</#list>

		<#list cacheFields as cacheField>
			objectOutput.writeObject(${cacheField.name});
		</#list>
	}

	<#list entity.regularEntityColumns as entityColumn>
		<#if !stringUtil.equals(entityColumn.type, "Blob")>
			<#if stringUtil.equals(entityColumn.type, "Date")>
				public long ${entityColumn.name};
			<#else>
				<#assign entityColumnPrimitiveType = serviceBuilder.getPrimitiveType(entityColumn.genericizedType) />

				public ${entityColumnPrimitiveType} ${entityColumn.name};
			</#if>
		</#if>
	</#list>

	<#list cacheFields as cacheField>
		public ${serviceBuilder.getGenericValue(cacheField.type)} ${cacheField.name};
	</#list>

	<#if entity.hasCompoundPK()>
		public transient ${entity.name}PK ${entity.PKVarName};
	</#if>

}