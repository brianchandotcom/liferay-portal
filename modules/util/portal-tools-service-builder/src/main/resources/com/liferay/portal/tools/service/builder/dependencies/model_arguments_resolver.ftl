<#if serviceBuilder.isVersionGTE_7_4_0()>
	package ${packagePath}.service.persistence.impl;

	import ${apiPackagePath}.model.${entity.name}Table;
	import ${packagePath}.model.impl.${entity.name}Impl;
	import ${packagePath}.model.impl.${entity.name}ModelImpl;

	import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
	import com.liferay.portal.kernel.dao.orm.FinderPath;
	import com.liferay.portal.kernel.model.BaseModel;
	import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

	import java.util.ArrayList;
	import java.util.List;
	import java.util.Map;
	import java.util.Objects;
	import java.util.concurrent.ConcurrentHashMap;

	import org.osgi.service.component.annotations.Component;
	<#assign columnBitmaskEnabled = (entity.databaseRegularEntityColumns?size &lt; 64) && !entity.hasEagerBlobColumn() />

	/**
	 * The arguments resolver class for retrieving value from ${entity.name}.
	 *
	 * @author ${author}
	<#if classDeprecated>
	 * @deprecated ${classDeprecatedComment}
	</#if>
	 * @generated
	 */
	<#if dependencyInjectorDS>
		@Component(
	<#else>
		@OSGiBeanProperties(
	</#if>
		property = {"class.name=${packagePath}.model.impl.${entity.name}Impl", "table.name=${entity.table}"}, service = ArgumentsResolver.class)
	public
<#else>
	private static
</#if>
class ${entity.name}ModelArgumentsResolver implements ArgumentsResolver {

	@Override
	public Object[] getArguments(FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn, boolean original) {
		String[] columnNames = finderPath.getColumnNames();

		if ((columnNames == null) || (columnNames.length == 0)) {
			if (baseModel.isNew()) {
				return new Object[0];
			}

			return null;
		}

		${entity.name}ModelImpl ${entity.variableName}ModelImpl = (${entity.name}ModelImpl)baseModel;

		<#if columnBitmaskEnabled>
			long columnBitmask = ${entity.variableName}ModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(${entity.variableName}ModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |= ${entity.variableName}ModelImpl.getColumnBitmask(columnName);
				}

				<#if entity.entityOrder??>
					if (finderPath.isBaseModelResult() && (${entity.name}PersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION == finderPath.getCacheName())) {
						finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
					}
				</#if>

				_finderPathColumnBitmasksCache.put(finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(${entity.variableName}ModelImpl, columnNames, original);
			}
		<#else>
			if (!checkColumn || _hasModifiedColumns(${entity.variableName}ModelImpl, columnNames)

			<#if entity.entityOrder??>
				|| _hasModifiedColumns(${entity.variableName}ModelImpl, _ORDER_BY_COLUMNS)
			</#if>

			) {
				return _getValue(${entity.variableName}ModelImpl, columnNames, original);
			}
		</#if>

		return null;
	}

	<#if serviceBuilder.isVersionGTE_7_4_0()>
		@Override
		public String getClassName() {
			return ${entity.name}Impl.class.getName();
		}

		@Override
		public String getTableName() {
			return ${entity.name}Table.INSTANCE.getTableName();
		}
	</#if>

	private static Object[] _getValue(${entity.name}ModelImpl ${entity.variableName}ModelImpl, String[] columnNames, boolean original) {
		Object[] arguments = new Object[columnNames.length];

		for (int i = 0; i < arguments.length; i ++) {
			String columnName = columnNames[i];

			if (original) {
				arguments[i] = ${entity.variableName}ModelImpl.getColumnOriginalValue(columnName);
			}
			else {
				arguments[i] = ${entity.variableName}ModelImpl.getColumnValue(columnName);
			}
		}

		return arguments;
	}

	<#if columnBitmaskEnabled>
		private static final Map<FinderPath, Long> _finderPathColumnBitmasksCache = new ConcurrentHashMap<>();
	<#else>
		private static boolean _hasModifiedColumns(${entity.name}ModelImpl ${entity.variableName}ModelImpl, String[] columnNames) {
			if (columnNames.length == 0) {
				return false;
			}

			for (String columnName : columnNames) {
				if (!Objects.equals(${entity.variableName}ModelImpl.getColumnOriginalValue(columnName), ${entity.variableName}ModelImpl.getColumnValue(columnName))) {
					return true;
				}
			}

			return false;
		}
	</#if>

	<#if entity.entityOrder??>
		<#if columnBitmaskEnabled>
			private static final long _ORDER_BY_COLUMNS_BITMASK;

			static {
				long orderByColumnsBitmask = 0;

				<#list entity.entityOrder.entityColumns as entityColumn>
					<#if !entity.PKEntityColumns?seq_contains(entityColumn)>
						orderByColumnsBitmask |= ${entity.name}ModelImpl.getColumnBitmask("${entityColumn.DBName}");
					</#if>
				</#list>

				_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
			}
		<#else>
			private static final String[] _ORDER_BY_COLUMNS;

			static {
				List<String> orderByColumns = new ArrayList<String>();

				<#if entity.entityOrder??>
					<#list entity.entityOrder.entityColumns as entityColumn>
						<#if !entity.PKEntityColumns?seq_contains(entityColumn)>
							orderByColumns.add("${entityColumn.DBName}");
						</#if>
					</#list>
				</#if>

				_ORDER_BY_COLUMNS = orderByColumns.toArray(new String[0]);
			}
		</#if>
	</#if>

}