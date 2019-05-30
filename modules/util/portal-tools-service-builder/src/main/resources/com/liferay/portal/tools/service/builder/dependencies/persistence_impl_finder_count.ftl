<#assign
	entityColumns = entityFinder.entityColumns
	entityFinderArrayableColsList = entityFinder.getArrayableColumns()
/>

/**
 * Returns the number of ${entity.humanNames} where ${entityFinder.getHumanConditions(false)}.
 *
<#list entityColumns as entityColumn>
 * @param ${entityColumn.name} the ${entityColumn.humanName}
</#list>
 * @return the number of matching ${entity.humanNames}
 */
@Override
public int countBy${entityFinder.name}(

<#list entityColumns as entityColumn>
	${entityColumn.type} ${entityColumn.name}

	<#if entityColumn_has_next>
		,
	</#if>
</#list>

) {
	<#list entityColumns as entityColumn>
		<#if stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
			${entityColumn.name} = Objects.toString(${entityColumn.name}, "");
		</#if>
	</#list>

	FinderPath finderPath =
		<#if !entityFinder.hasCustomComparator()>
			_finderPathCountBy${entityFinder.name};
		<#else>
			_finderPathWithPaginationCountBy${entityFinder.name};
		</#if>

	Object[] finderArgs = new Object[] {
		<#list entityColumns as entityColumn>
			<#if stringUtil.equals(entityColumn.type, "Date")>
				_getTime(${entityColumn.name})
			<#else>
				${entityColumn.name}
			</#if>

			<#if entityColumn_has_next>
				,
			</#if>
		</#list>
	};

	Long count = (Long)${finderCache}.getResult(finderPath, finderArgs, this);

	if (count == null) {
		<#include "persistence_impl_count_by_query.ftl">

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			<@finderQPos />

			count = (Long)q.uniqueResult();

			${finderCache}.putResult(finderPath, finderArgs, count);
		}
		catch (Exception e) {
			${finderCache}.removeResult(finderPath, finderArgs);

			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	return count.intValue();
}

<#if entityFinder.hasArrayableOperator() && !entityFinder.hasArrayablePagination()>
	/**
	 * Returns the number of ${entity.humanNames} where ${entityFinder.getHumanConditions(true)}.
	 *
	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
	 * @param ${entityColumn.names} the ${entityColumn.humanNames}
		<#else>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#if>
	</#list>
	 * @return the number of matching ${entity.humanNames}
	 */
	@Override
	public int countBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
			${entityColumn.type}[] ${entityColumn.names}
		<#else>
			${entityColumn.type} ${entityColumn.name}
		</#if>

		<#if entityColumn_has_next>
			,
		</#if>
	</#list>

	) {
		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				if (${entityColumn.names} == null) {
					${entityColumn.names} = new ${entityColumn.type}[0];
				}
				else if (${entityColumn.names}.length > 1) {
					<#if stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
						for (int i = 0; i < ${entityColumn.names}.length; i++) {
							${entityColumn.names}[i] = Objects.toString(${entityColumn.names}[i], "");
						}
					</#if>

					<#if serviceBuilder.isVersionGTE_7_2_0()>
						${entityColumn.names} = ArrayUtil.sortedUnique(${entityColumn.names});
					<#else>
						${entityColumn.names} =
							<#if stringUtil.equals(entityColumn.type, "String") && !entityColumn.isConvertNull()>
								ArrayUtil.distinct(${entityColumn.names}, NULL_SAFE_STRING_COMPARATOR);
							<#else>
								ArrayUtil.unique(${entityColumn.names});
							</#if>

						<#if stringUtil.equals(entityColumn.type, "String") && !entityColumn.isConvertNull()>
							Arrays.sort(${entityColumn.names}, NULL_SAFE_STRING_COMPARATOR);
						<#else>
							Arrays.sort(${entityColumn.names});
						</#if>
					</#if>
				}
			<#elseif stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
				${entityColumn.name} = Objects.toString(${entityColumn.name}, "");
			</#if>
		</#list>

		Object[] finderArgs = new Object[] {
			<#list entityColumns as entityColumn>
				<#if entityColumn.hasArrayableOperator()>
					StringUtil.merge(${entityColumn.names})
				<#elseif stringUtil.equals(entityColumn.type, "Date")>
					_getTime(${entityColumn.name})
				<#else>
					${entityColumn.name}
				</#if>

				<#if entityColumn_has_next>
					,
				</#if>
			</#list>
		};

		Long count = (Long)${finderCache}.getResult(_finderPathWithPaginationCountBy${entityFinder.name}, finderArgs, this);

		if (count == null) {
			<#include "persistence_impl_count_by_arrayable_query.ftl">

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				<#if bindParameter(entityColumns)>
					QueryPos qPos = QueryPos.getInstance(q);
				</#if>

				<@finderQPos _arrayable=true />

				count = (Long)q.uniqueResult();

				${finderCache}.putResult(_finderPathWithPaginationCountBy${entityFinder.name}, finderArgs, count);
			}
			catch (Exception e) {
				${finderCache}.removeResult(_finderPathWithPaginationCountBy${entityFinder.name}, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}
</#if>

<#if entityFinder.hasArrayableOperator() && entityFinder.hasArrayablePagination()>
	/**
	 * Returns the number of ${entity.humanNames} where ${entityFinder.getHumanConditions(true)}.
	 *
	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
	 * @param ${entityColumn.names} the ${entityColumn.humanNames}
		<#else>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#if>
	</#list>
	 * @return the number of matching ${entity.humanNames}
	 */
	@Override
	public int countBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
			${entityColumn.type}[] ${entityColumn.names}
		<#else>
			${entityColumn.type} ${entityColumn.name}
		</#if>

		<#if entityColumn_has_next>
			,
		</#if>
	</#list>

	) {
		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				if (${entityColumn.names} == null) {
					${entityColumn.names} = new ${entityColumn.type}[0];
				}
				else if (${entityColumn.names}.length > 1) {
					<#if stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
						for (int i = 0; i < ${entityColumn.names}.length; i++) {
							${entityColumn.names}[i] = Objects.toString(${entityColumn.names}[i], "");
						}
					</#if>

					<#if serviceBuilder.isVersionGTE_7_2_0()>
						${entityColumn.names} = ArrayUtil.sortedUnique(${entityColumn.names});
					<#else>
						${entityColumn.names} =
							<#if stringUtil.equals(entityColumn.type, "String") && !entityColumn.isConvertNull()>
								ArrayUtil.distinct(${entityColumn.names}, NULL_SAFE_STRING_COMPARATOR);
							<#else>
								ArrayUtil.unique(${entityColumn.names});
							</#if>

						<#if stringUtil.equals(entityColumn.type, "String") && !entityColumn.isConvertNull()>
							Arrays.sort(${entityColumn.names}, NULL_SAFE_STRING_COMPARATOR);
						<#else>
							Arrays.sort(${entityColumn.names});
						</#if>
					</#if>
				}
			<#elseif stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
				${entityColumn.name} = Objects.toString(${entityColumn.name}, "");
			</#if>
		</#list>

		Object[] finderArgs = new Object[] {
			<#list entityColumns as entityColumn>
				<#if entityColumn.hasArrayableOperator()>
					StringUtil.merge(${entityColumn.names})
				<#else>
					${entityColumn.name}
				</#if>

				<#if entityColumn_has_next>
					,
				</#if>
			</#list>
		};

		Long count = (Long)${finderCache}.getResult(_finderPathWithPaginationCountBy${entityFinder.name}, finderArgs, this);

		if (count == null) {
			try {
				if ((databaseInMaxParameters > 0) && (<#list entityFinderArrayableColsList as arrayablefinderCol>
							(${arrayablefinderCol.names}.length > databaseInMaxParameters)

							<#if arrayablefinderCol_has_next>
								||
							</#if>
						</#list>)) {
						count = Long.valueOf(0);

						<#list entityFinderArrayableColsList as arrayablefinderCol>
							${arrayablefinderCol.type}[][] ${arrayablefinderCol.names}Pages = (${arrayablefinderCol.type}[][])ArrayUtil.split(${arrayablefinderCol.names}, databaseInMaxParameters);
						</#list>

						<#list entityFinderArrayableColsList as arrayablefinderCol>
							for (${arrayablefinderCol.type}[] ${arrayablefinderCol.names}Page : ${arrayablefinderCol.names}Pages) {
						</#list>

							count += Long.valueOf(_countBy${entityFinder.name}(

							<#list entityColumns as entityColumn>
								<#if entityColumn.hasArrayableOperator()>
									${entityColumn.names}Page
								<#else>
									${entityColumn.name}
								</#if>

								<#if entityColumn_has_next>
									,
								</#if>
							</#list>));

					<#list entityFinderArrayableColsList as arrayablefinderCol>
						}
					</#list>
					}
					else {
						count = Long.valueOf(_countBy${entityFinder.name}(

						<#list entityColumns as entityColumn>
							<#if entityColumn.hasArrayableOperator()>
								${entityColumn.names}
							<#else>
								${entityColumn.name}
							</#if>

							<#if entityColumn_has_next>
								,
							</#if>
						</#list>));
					}

					${finderCache}.putResult(_finderPathWithPaginationCountBy${entityFinder.name}, finderArgs, count);
			}
			catch (Exception e) {
				${finderCache}.removeResult(_finderPathWithPaginationCountBy${entityFinder.name}, finderArgs);

				throw processException(e);
			}
		}

		return count.intValue();
	}

	private int _countBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
			${entityColumn.type}[] ${entityColumn.names}
		<#else>
			${entityColumn.type} ${entityColumn.name}
		</#if>

		<#if entityColumn_has_next>
			,
		</#if>
	</#list>

	) {
		Long count = null;

		<#include "persistence_impl_count_by_arrayable_query.ftl">

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			<#if bindParameter(entityColumns)>
				QueryPos qPos = QueryPos.getInstance(q);
			</#if>

			<@finderQPos _arrayable=true />

			count = (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return count.intValue();
	}
</#if>

<#if entity.isPermissionCheckEnabled(entityFinder)>
	/**
	 * Returns the number of ${entity.humanNames} that the user has permission to view where ${entityFinder.getHumanConditions(false)}.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @return the number of matching ${entity.humanNames} that the user has permission to view
	 */
	@Override
	public int filterCountBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name}

		<#if entityColumn_has_next>
			,
		</#if>
	</#list>

	) {
		<#if entityFinder.hasEntityColumn("groupId")>
			if (!InlineSQLHelperUtil.isEnabled(groupId)) {
		<#elseif entityFinder.hasEntityColumn("companyId")>
			if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
		<#else>
			if (!InlineSQLHelperUtil.isEnabled()) {
		</#if>

			return countBy${entityFinder.name}(

			<#list entityColumns as entityColumn>
				${entityColumn.name}

				<#if entityColumn_has_next>
					,
				</#if>
			</#list>

			);
		}

		<#list entityColumns as entityColumn>
			<#if stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
				${entityColumn.name} = Objects.toString(${entityColumn.name}, "");
			</#if>
		</#list>

		<#if entity.isPermissionedModel()>
			<#include "persistence_impl_count_by_query.ftl">

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN<#if entityFinder.hasEntityColumn("groupId")>, groupId</#if>);

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				<@finderQPos />

				Long count = (Long)q.uniqueResult();

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		<#else>
			StringBundler query = new StringBundler(${entityColumns?size + 1});

			query.append(_FILTER_SQL_COUNT_${entity.alias?upper_case}_WHERE);

			<#assign sqlQuery = true />

			<#include "persistence_impl_finder_cols.ftl">

			<#assign sqlQuery = false />

			String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN<#if entityFinder.hasEntityColumn("groupId")>, groupId</#if>);

			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSynchronizedSQLQuery(sql);

				q.addScalar(COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				<@finderQPos />

				Long count = (Long)q.uniqueResult();

				return count.intValue();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		</#if>
	}

	<#if entityFinder.hasArrayableOperator()>
		/**
		 * Returns the number of ${entity.humanNames} that the user has permission to view where ${entityFinder.getHumanConditions(true)}.
		 *
		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
		 * @param ${entityColumn.names} the ${entityColumn.humanNames}
			<#else>
		 * @param ${entityColumn.name} the ${entityColumn.humanName}
			</#if>
		</#list>
		 * @return the number of matching ${entity.humanNames} that the user has permission to view
		 */
		@Override
		public int filterCountBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				${entityColumn.type}[] ${entityColumn.names}
			<#else>
				${entityColumn.type} ${entityColumn.name}
			</#if>

			<#if entityColumn_has_next>
				,
			</#if>
		</#list>

		) {
			<#if entityFinder.hasEntityColumn("groupId")>
				if (!InlineSQLHelperUtil.isEnabled(
					<#if entityFinder.getEntityColumn("groupId").hasArrayableOperator()>
						groupIds
					<#else>
						groupId
					</#if>
				)) {
			<#elseif entityFinder.hasEntityColumn("companyId")>
				if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			<#else>
				if (!InlineSQLHelperUtil.isEnabled()) {
			</#if>

				return countBy${entityFinder.name}(

				<#list entityColumns as entityColumn>
					<#if entityColumn.hasArrayableOperator()>
						${entityColumn.names}
					<#else>
						${entityColumn.name}
					</#if>

					<#if entityColumn_has_next>
						,
					</#if>
				</#list>

				);
			}

			<#list entityColumns as entityColumn>
				<#if entityColumn.hasArrayableOperator()>
					if (${entityColumn.names} == null) {
						${entityColumn.names} = new ${entityColumn.type}[0];
					}
					else if (${entityColumn.names}.length > 1) {
						<#if stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
							for (int i = 0; i < ${entityColumn.names}.length; i++) {
								${entityColumn.names}[i] = Objects.toString(${entityColumn.names}[i], "");
							}
						</#if>

						<#if serviceBuilder.isVersionGTE_7_2_0()>
							${entityColumn.names} = ArrayUtil.sortedUnique(${entityColumn.names});
						<#else>
							${entityColumn.names} =
								<#if stringUtil.equals(entityColumn.type, "String") && !entityColumn.isConvertNull()>
									ArrayUtil.distinct(${entityColumn.names}, NULL_SAFE_STRING_COMPARATOR);
								<#else>
									ArrayUtil.unique(${entityColumn.names});
								</#if>

							<#if stringUtil.equals(entityColumn.type, "String") && !entityColumn.isConvertNull()>
								Arrays.sort(${entityColumn.names}, NULL_SAFE_STRING_COMPARATOR);
							<#else>
								Arrays.sort(${entityColumn.names});
							</#if>
						</#if>
					}
				<#elseif stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
					${entityColumn.name} = Objects.toString(${entityColumn.name}, "");
				</#if>
			</#list>

			<#if entity.isPermissionedModel()>
				<#include "persistence_impl_count_by_arrayable_query.ftl">

				String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN

				<#if entityFinder.hasEntityColumn("groupId")>,
					<#if entityFinder.getEntityColumn("groupId").hasArrayableOperator()>
						groupIds
					<#else>
						groupId
					</#if>
				</#if>);

				Session session = null;

				try {
					session = openSession();

					Query q = session.createQuery(sql);

					<#if bindParameter(entityColumns)>
						QueryPos qPos = QueryPos.getInstance(q);
					</#if>

					<@finderQPos _arrayable=true />

					Long count = (Long)q.uniqueResult();

					return count.intValue();
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			<#else>
				StringBundler query = new StringBundler();

				query.append(_FILTER_SQL_COUNT_${entity.alias?upper_case}_WHERE);

				<#assign sqlQuery = true />

				<#include "persistence_impl_finder_arrayable_cols.ftl">

				<#assign sqlQuery = false />

				String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN

				<#if entityFinder.hasEntityColumn("groupId")>,
					<#if entityFinder.getEntityColumn("groupId").hasArrayableOperator()>
						groupIds
					<#else>
						groupId
					</#if>
				</#if>);

				Session session = null;

				try {
					session = openSession();

					SQLQuery q = session.createSynchronizedSQLQuery(sql);

					q.addScalar(COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

					<#if bindParameter(entityColumns)>
						QueryPos qPos = QueryPos.getInstance(q);
					</#if>

					<@finderQPos _arrayable=true />

					Long count = (Long)q.uniqueResult();

					return count.intValue();
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			</#if>
		}
	</#if>
</#if>