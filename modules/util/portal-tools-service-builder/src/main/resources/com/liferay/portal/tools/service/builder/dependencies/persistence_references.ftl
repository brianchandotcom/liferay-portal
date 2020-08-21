	@Override
	@Reference(
		target = ${portletShortName}PersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		<#if serviceBuilder.isVersionLTE_7_2_0()>
			<#if !entity.isCacheEnabled()>
				entityCacheEnabled = false;
				finderCacheEnabled = false;
			</#if>

			super.setConfiguration(configuration);

			<#if persistence>
				_columnBitmaskEnabled = GetterUtil.getBoolean(configuration.get("value.object.column.bitmask.enabled.${apiPackagePath}.model.${entity.name}"), true);
			</#if>
		</#if>
	}

	@Override
	@Reference(
		target = ${portletShortName}PersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ${portletShortName}PersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}