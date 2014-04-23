package ${packagePath}.model.impl;

import ${packagePath}.model.${entity.name};

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;
import java.util.Map;

/**
 * The cache model class for representing ${entity.name} in entity cache.
 *
 * @author ${author}
 * @see ${entity.name}
 * @generated
 */
public class ${entity.name}CacheModel implements CacheModel<${entity.name}>, Externalizable
	<#if entity.isMvccEnabled()>
		, MVCCModel
	</#if>

	{

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
		StringBundler sb = new StringBundler(${(entity.regularColList?size - entity.blobList?size) * 2 + 1});

		<#list entity.regularColList as column>
			<#if column.type != "Blob">
				<#if column_index == 0>
					sb.append("{${column.name}=");
					sb.append(${column.name});
				<#elseif column_has_next>
					sb.append(", ${column.name}=");
					sb.append(${column.name});
				<#else>
					sb.append(", ${column.name}=");
					sb.append(${column.name});
					sb.append("}");
				</#if>
			</#if>
		</#list>

		return sb.toString();
	}

	@Override
	public ${entity.name} toEntityModel() {
		${entity.name}Impl ${entity.varName}Impl = new ${entity.name}Impl();

		<#list entity.regularColList as column>
			<#if column.type != "Blob">
				<#if column.type == "Date">
					if (${column.name} == Long.MIN_VALUE) {
						${entity.varName}Impl.set${column.methodName}(null);
					}
					else {
						${entity.varName}Impl.set${column.methodName}(new Date(${column.name}));
					}
				<#else>
					<#if column.type == "String">
						if (${column.name} == null) {
							${entity.varName}Impl.set${column.methodName}(StringPool.BLANK);
						}
						else {
							${entity.varName}Impl.set${column.methodName}(${column.name});
						}
					<#else>
						${entity.varName}Impl.set${column.methodName}(${column.name});
					</#if>
				</#if>
			</#if>
		</#list>

		${entity.varName}Impl.resetOriginalValues();

		<#list cacheFields as cacheField>
			<#assign methodName = textFormatter.format(serviceBuilder.getVariableName(cacheField), 6)>

			${entity.varName}Impl.set${methodName}(${cacheField.name});
		</#list>

		return ${entity.varName}Impl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws
		<#assign throwsClassNotFoundException = false>

		<#list entity.regularColList as column>
			<#if column.primitiveType>
			<#elseif column.type == "Date">
			<#elseif column.type == "String">
			<#elseif column.type != "Blob">
				<#assign throwsClassNotFoundException = true>
			</#if>
		</#list>

		<#if (cacheFields?size > 0)>
			<#assign throwsClassNotFoundException = true>
		</#if>

		<#if throwsClassNotFoundException>
			ClassNotFoundException,
		</#if>

		IOException {

		<#list entity.regularColList as column>
			<#if column.primitiveType>
				${column.name} = objectInput.read${textFormatter.format(column.type, 6)}();
			<#elseif column.type == "Date">
				${column.name} = objectInput.readLong();
			<#elseif column.type == "String">
				${column.name} = objectInput.readUTF();
			<#elseif column.type != "Blob">
				${column.name} = (${column.type})objectInput.readObject();
			</#if>
		</#list>

		<#list cacheFields as cacheField>
			${cacheField.name} = (${cacheField.type.genericValue})objectInput.readObject();
		</#list>
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		<#list entity.regularColList as column>
			<#if column.primitiveType>
				objectOutput.write${textFormatter.format(column.type, 6)}(${column.name});
			<#elseif column.type == "Date">
				objectOutput.writeLong(${column.name});
			<#elseif column.type == "String">
				if (${column.name} == null) {
					objectOutput.writeUTF(StringPool.BLANK);
				}
				else {
					objectOutput.writeUTF(${column.name});
				}
			<#elseif column.type != "Blob">
				objectOutput.writeObject(${column.name});
			</#if>
		</#list>

		<#list cacheFields as cacheField>
			objectOutput.writeObject(${cacheField.name});
		</#list>
	}

	<#list entity.regularColList as column>
		<#if column.type != "Blob">
			<#if column.type == "Date">
				public long ${column.name};
			<#else>
				public ${column.type} ${column.name};
			</#if>
		</#if>
	</#list>

	<#list cacheFields as cacheField>
		public ${cacheField.type.genericValue} ${cacheField.name};
	</#list>

}