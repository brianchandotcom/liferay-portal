<#assign
	entityColumns = entityFinder.entityColumns
	entityFinderArrayableColsList = entityFinder.getArrayableColumns()
/>

<#--
Basic Cases Table:

+---------------------------+-------------------------------+-------------------------------+
|							|	entityFinder.isCollection()		|	!entityFinder.isCollection()		|
+---------------------------+-------------------------------+-------------------------------+
|	entityFinder.isUnique()		|			Case 1				|			Case 2				|
+---------------------------+-------------------------------+-------------------------------+
|	!entityFinder.isUnique()		|			Case 3				|			Case 4				|
+---------------------------+-------------------------------+-------------------------------+

Combination Cases Table 1:

+---------------------------+-------------------------------+-------------------------------+
|							|	entityFinder.isCollection()		|	!entityFinder.isCollection()		|
+---------------------------+---------------------------------------------------------------+
|	entityFinder.isUnique()		|							Case 5								|
+---------------------------+---------------------------------------------------------------+
|	!entityFinder.isUnique()		|							Case 6								|
+---------------------------+---------------------------------------------------------------+

Combination Cases Table 2:

+---------------------------+-------------------------------+-------------------------------+
|							|	entityFinder.isCollection()		|	!entityFinder.isCollection()		|
+---------------------------+-------------------------------+-------------------------------+
|	entityFinder.isUnique()		|								|								|
+---------------------------|			Case 7				|			Case 8				|
|	!entityFinder.isUnique()		|								|								|
+---------------------------+-------------------------------+-------------------------------+

Combination Cases Table 3:

+---------------------------+-------------------------------+-------------------------------+
|							|	entityFinder.isCollection()		|	!entityFinder.isCollection()		|
+---------------------------+-------------------------------+-------------------------------+
|	entityFinder.isUnique()		|																|
+---------------------------|--------------------------------			Case 9				|
|	!entityFinder.isUnique()		|								|								|
+---------------------------+-------------------------------+-------------------------------+

There are a total of 9 cases. The first 4 cases are the basic cases as show in
the first table.

The additional combination case tables allow us to group the basic cases. For
example:

A combination of case 1 and case 2 is grouped as case 5.

A combination of case 3 and case 4 is grouped as case 6.

A combination of case 1 and case 3 is grouped as case 7.

A combination of case 2 and case 4 is grouped as case 8.

A combination of case 1, case 2, and case 4 is grouped as case 9.

Grouping the basic cases allows us to write the finder implementation with as
little duplicate code as possible.

entityFinder.isUnique() means a literal unique finder because it generates a unique
index at the database level. !entityFinder.isCollection() means a conceptual unique
that may or may not be enforced with a unique index at the database level. Case
9 can be considered a union of the literal and conceptual unique finders.
-->

<#-- Case 1: entityFinder.isCollection() && entityFinder.isUnique() -->

<#if entityFinder.isCollection() && entityFinder.isUnique()>
</#if>

<#-- Case 2: !entityFinder.isCollection() && entityFinder.isUnique() -->

<#if !entityFinder.isCollection() && entityFinder.isUnique()>
</#if>

<#-- Case 3: entityFinder.isCollection() && !entityFinder.isUnique() -->

<#if entityFinder.isCollection() && !entityFinder.isUnique()>
	/**
	 * Returns all the ${entity.humanNames} where ${entityFinder.getHumanConditions(false)}.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @return the matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name}

		<#if entityColumn_has_next>
			,
		</#if>
	</#list>

	) {
		return findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name},
		</#list>

		QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ${entity.humanNames} where ${entityFinder.getHumanConditions(false)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @return the range of matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name},
	</#list>

	int start, int end) {
		return findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name},
		</#list>

		start, end, null);
	}

	/**
	 * Returns an ordered range of all the ${entity.humanNames} where ${entityFinder.getHumanConditions(false)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name},
	</#list>

	int start, int end, OrderByComparator<${entity.name}> orderByComparator) {
		return findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name},
		</#list>

		start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ${entity.humanNames} where ${entityFinder.getHumanConditions(false)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name},
	</#list>

	int start, int end, OrderByComparator<${entity.name}> orderByComparator, boolean useFinderCache) {
		<#list entityColumns as entityColumn>
			<#if stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
				${entityColumn.name} = Objects.toString(${entityColumn.name}, "");
			</#if>
		</#list>

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		<#if !entityFinder.hasCustomComparator()>
			if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (orderByComparator == null)) {
				pagination = false;

				if (useFinderCache) {
					finderPath = _finderPathWithoutPaginationFindBy${entityFinder.name};
					finderArgs = new Object[] {
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
				}
			}
			else if (useFinderCache) {
		</#if>

		finderPath = _finderPathWithPaginationFindBy${entityFinder.name};
		finderArgs = new Object[] {
			<#list entityColumns as entityColumn>
				<#if stringUtil.equals(entityColumn.type, "Date")>
					_getTime(${entityColumn.name}),
				<#else>
					${entityColumn.name},
				</#if>
			</#list>

			start, end, orderByComparator
		};

		<#if !entityFinder.hasCustomComparator()>
			}
		</#if>

		List<${entity.name}> list = null;

		if (useFinderCache) {
			list = (List<${entity.name}>)${finderCache}.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (${entity.name} ${entity.varName} : list) {
					if (
						<#list entityColumns as entityColumn>
							<#include "persistence_impl_finder_field_comparator.ftl">

							<#if entityColumn_has_next>
								||
							</#if>
						</#list>
					) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			<#assign checkPagination = true />

			<#include "persistence_impl_find_by_query.ftl">

			<#assign checkPagination = false />

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				<@finderQPos />

				if (!pagination) {
					list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					${finderCache}.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					${finderCache}.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first ${entity.humanName} in the ordered set where ${entityFinder.getHumanConditions(false)}.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ${entity.humanName}
	 * @throws ${noSuchEntity}Exception if a matching ${entity.humanName} could not be found
	 */
	@Override
	public ${entity.name} findBy${entityFinder.name}_First(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name},
	</#list>

	OrderByComparator<${entity.name}> orderByComparator) throws ${noSuchEntity}Exception {
		${entity.name} ${entity.varName} = fetchBy${entityFinder.name}_First(

		<#list entityColumns as entityColumn>
			${entityColumn.name},
		</#list>

		orderByComparator);

		if (${entity.varName} != null) {
			return ${entity.varName};
		}

		StringBundler msg = new StringBundler(${(entityColumns?size * 2) + 2});

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		<#list entityColumns as entityColumn>
			msg.append("<#if entityColumn_index != 0>, </#if>${entityColumn.name}=");
			msg.append(${entityColumn.name});

			<#if !entityColumn_has_next>
				msg.append("}");
			</#if>
		</#list>

		throw new ${noSuchEntity}Exception(msg.toString());
	}

	/**
	 * Returns the first ${entity.humanName} in the ordered set where ${entityFinder.getHumanConditions(false)}.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
	 */
	@Override
	public ${entity.name} fetchBy${entityFinder.name}_First(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name},
	</#list>

	OrderByComparator<${entity.name}> orderByComparator) {
		List<${entity.name}> list = findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name},
		</#list>

		0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ${entity.humanName} in the ordered set where ${entityFinder.getHumanConditions(false)}.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ${entity.humanName}
	 * @throws ${noSuchEntity}Exception if a matching ${entity.humanName} could not be found
	 */
	@Override
	public ${entity.name} findBy${entityFinder.name}_Last(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name},
	</#list>

	OrderByComparator<${entity.name}> orderByComparator) throws ${noSuchEntity}Exception {
		${entity.name} ${entity.varName} = fetchBy${entityFinder.name}_Last(

		<#list entityColumns as entityColumn>
			${entityColumn.name},
		</#list>

		orderByComparator);

		if (${entity.varName} != null) {
			return ${entity.varName};
		}

		StringBundler msg = new StringBundler(${(entityColumns?size * 2) + 2});

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		<#list entityColumns as entityColumn>
			msg.append("<#if entityColumn_index != 0>, </#if>${entityColumn.name}=");
			msg.append(${entityColumn.name});

			<#if !entityColumn_has_next>
				msg.append("}");
			</#if>
		</#list>

		throw new ${noSuchEntity}Exception(msg.toString());
	}

	/**
	 * Returns the last ${entity.humanName} in the ordered set where ${entityFinder.getHumanConditions(false)}.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
	 */
	@Override
	public ${entity.name} fetchBy${entityFinder.name}_Last(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name},
	</#list>

	OrderByComparator<${entity.name}> orderByComparator) {
		int count = countBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name}

			<#if entityColumn_has_next>
				,
			</#if>
		</#list>

		);

		if (count == 0) {
			return null;
		}

		List<${entity.name}> list = findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name},
		</#list>

		count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	<#if !entityFinder.hasEntityColumn(entity.PKVarName)>
		/**
		 * Returns the ${entity.humanNames} before and after the current ${entity.humanName} in the ordered set where ${entityFinder.getHumanConditions(false)}.
		 *
		 * @param ${entity.PKVarName} the primary key of the current ${entity.humanName}
		<#list entityColumns as entityColumn>
		 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#list>
		 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
		 * @return the previous, current, and next ${entity.humanName}
		 * @throws ${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
		 */
		@Override
		public ${entity.name}[] findBy${entityFinder.name}_PrevAndNext(${entity.PKClassName} ${entity.PKVarName},

		<#list entityColumns as entityColumn>
			${entityColumn.type} ${entityColumn.name},
		</#list>

		OrderByComparator<${entity.name}> orderByComparator) throws ${noSuchEntity}Exception {
			<#list entityColumns as entityColumn>
				<#if stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
					${entityColumn.name} = Objects.toString(${entityColumn.name}, "");
				</#if>
			</#list>

			${entity.name} ${entity.varName} = findByPrimaryKey(${entity.PKVarName});

			Session session = null;

			try {
				session = openSession();

				${entity.name}[] array = new ${entity.name}Impl[3];

				array[0] =
					getBy${entityFinder.name}_PrevAndNext(
						session, ${entity.varName},

						<#list entityColumns as entityColumn>
							${entityColumn.name},
						</#list>

						orderByComparator, true);

				array[1] = ${entity.varName};

				array[2] =
					getBy${entityFinder.name}_PrevAndNext(
						session, ${entity.varName},

						<#list entityColumns as entityColumn>
							${entityColumn.name},
						</#list>

						orderByComparator, false);

				return array;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		protected ${entity.name} getBy${entityFinder.name}_PrevAndNext(
			Session session, ${entity.name} ${entity.varName},

			<#list entityColumns as entityColumn>
				${entityColumn.type} ${entityColumn.name},
			</#list>

			OrderByComparator<${entity.name}> orderByComparator, boolean previous) {

			<#include "persistence_impl_get_by_prev_and_next_query.ftl">

			String sql = query.toString();

			Query q = session.createQuery(sql);

			q.setFirstResult(0);
			q.setMaxResults(2);

			QueryPos qPos = QueryPos.getInstance(q);

			<@finderQPos />

			if (orderByComparator != null) {
				for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(${entity.varName})) {
					qPos.add(orderByConditionValue);
				}
			}

			List<${entity.name}> list = q.list();

			if (list.size() == 2) {
				return list.get(1);
			}
			else {
				return null;
			}
		}
	</#if>

	<#if entity.isPermissionCheckEnabled(entityFinder)>
		/**
		 * Returns all the ${entity.humanNames} that the user has permission to view where ${entityFinder.getHumanConditions(false)}.
		 *
		<#list entityColumns as entityColumn>
		 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#list>
		 * @return the matching ${entity.humanNames} that the user has permission to view
		 */
		@Override
		public List<${entity.name}> filterFindBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.type} ${entityColumn.name}

			<#if entityColumn_has_next>
				,
			</#if>
		</#list>

		) {
			return filterFindBy${entityFinder.name}(

			<#list entityColumns as entityColumn>
				${entityColumn.name},
			</#list>

			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}

		/**
		 * Returns a range of all the ${entity.humanNames} that the user has permission to view where ${entityFinder.getHumanConditions(false)}.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		<#list entityColumns as entityColumn>
		 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#list>
		 * @param start the lower bound of the range of ${entity.humanNames}
		 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
		 * @return the range of matching ${entity.humanNames} that the user has permission to view
		 */
		@Override
		public List<${entity.name}> filterFindBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.type} ${entityColumn.name},
		</#list>

		int start, int end) {
			return filterFindBy${entityFinder.name}(

			<#list entityColumns as entityColumn>
				${entityColumn.name},
			</#list>

			start, end, null);
		}

		/**
		 * Returns an ordered range of all the ${entity.humanNames} that the user has permissions to view where ${entityFinder.getHumanConditions(false)}.
		 *
		 * <p>
		 * <#include "range_comment.ftl">
		 * </p>
		 *
		<#list entityColumns as entityColumn>
		 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#list>
		 * @param start the lower bound of the range of ${entity.humanNames}
		 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
		 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
		 * @return the ordered range of matching ${entity.humanNames} that the user has permission to view
		 */
		@Override
		public List<${entity.name}> filterFindBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.type} ${entityColumn.name},
		</#list>

		int start, int end, OrderByComparator<${entity.name}> orderByComparator) {
			<#if entityFinder.hasEntityColumn("groupId")>
				if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			<#elseif entityFinder.hasEntityColumn("companyId")>
				if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			<#else>
				if (!InlineSQLHelperUtil.isEnabled()) {
			</#if>

				return findBy${entityFinder.name}(

				<#list entityColumns as entityColumn>
					${entityColumn.name},
				</#list>

				start, end, orderByComparator);
			}

			<#list entityColumns as entityColumn>
				<#if stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
					${entityColumn.name} = Objects.toString(${entityColumn.name}, "");
				</#if>
			</#list>

			<#if entity.isPermissionedModel()>
				<#include "persistence_impl_find_by_query.ftl">

				String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN<#if entityFinder.hasEntityColumn("groupId")>, groupId</#if>);

				Session session = null;

				try {
					session = openSession();

					Query q = session.createQuery(sql);

					QueryPos qPos = QueryPos.getInstance(q);

					<@finderQPos />

					return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			<#else>
				StringBundler query = null;

				if (orderByComparator != null) {
					query = new StringBundler(${entityColumns?size + 2} + (orderByComparator.getOrderByFields().length * 2));
				}
				else {
					query = new StringBundler(${entityColumns?size + 3});
				}

				if (getDB().isSupportsInlineDistinct()) {
					query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE);
				}
				else {
					query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1);
				}

				<#assign sqlQuery = true />

				<#include "persistence_impl_finder_cols.ftl">

				<#assign sqlQuery = false />

				if (!getDB().isSupportsInlineDistinct()) {
					query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2);
				}

				if (orderByComparator != null) {
					if (getDB().isSupportsInlineDistinct()) {
						appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
					}
					else {
						appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
					}
				}
				else {
					if (getDB().isSupportsInlineDistinct()) {
						query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
					}
					else {
						query.append(${entity.name}ModelImpl.ORDER_BY_SQL);
					}
				}

				String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN<#if entityFinder.hasEntityColumn("groupId")>, groupId</#if>);

				Session session = null;

				try {
					session = openSession();

					SQLQuery q = session.createSynchronizedSQLQuery(sql);

					if (getDB().isSupportsInlineDistinct()) {
						q.addEntity(_FILTER_ENTITY_ALIAS, ${entity.name}Impl.class);
					}
					else {
						q.addEntity(_FILTER_ENTITY_TABLE, ${entity.name}Impl.class);
					}

					QueryPos qPos = QueryPos.getInstance(q);

					<@finderQPos />

					return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			</#if>
		}

		<#if !entityFinder.hasEntityColumn(entity.PKVarName)>
			/**
			 * Returns the ${entity.humanNames} before and after the current ${entity.humanName} in the ordered set of ${entity.humanNames} that the user has permission to view where ${entityFinder.getHumanConditions(false)}.
			 *
			 * @param ${entity.PKVarName} the primary key of the current ${entity.humanName}
			<#list entityColumns as entityColumn>
			 * @param ${entityColumn.name} the ${entityColumn.humanName}
			</#list>
			 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
			 * @return the previous, current, and next ${entity.humanName}
			 * @throws ${noSuchEntity}Exception if a ${entity.humanName} with the primary key could not be found
			 */
			@Override
			public ${entity.name}[] filterFindBy${entityFinder.name}_PrevAndNext(${entity.PKClassName} ${entity.PKVarName},

			<#list entityColumns as entityColumn>
				${entityColumn.type} ${entityColumn.name},
			</#list>

			OrderByComparator<${entity.name}> orderByComparator) throws ${noSuchEntity}Exception {
				<#if entityFinder.hasEntityColumn("groupId")>
					if (!InlineSQLHelperUtil.isEnabled(groupId)) {
				<#elseif entityFinder.hasEntityColumn("companyId")>
					if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
				<#else>
					if (!InlineSQLHelperUtil.isEnabled()) {
				</#if>

					return findBy${entityFinder.name}_PrevAndNext(${entity.PKVarName},

					<#list entityColumns as entityColumn>
						${entityColumn.name},
					</#list>

					orderByComparator);
				}

				<#list entityColumns as entityColumn>
					<#if stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
						${entityColumn.name} = Objects.toString(${entityColumn.name}, "");
					</#if>
				</#list>

				${entity.name} ${entity.varName} = findByPrimaryKey(${entity.PKVarName});

				Session session = null;

				try {
					session = openSession();

					${entity.name}[] array = new ${entity.name}Impl[3];

					array[0] =
						filterGetBy${entityFinder.name}_PrevAndNext(
							session, ${entity.varName},

							<#list entityColumns as entityColumn>
								${entityColumn.name},
							</#list>

							orderByComparator, true);

					array[1] = ${entity.varName};

					array[2] =
						filterGetBy${entityFinder.name}_PrevAndNext(
							session, ${entity.varName},

							<#list entityColumns as entityColumn>
								${entityColumn.name},
							</#list>

							orderByComparator, false);

					return array;
				}
				catch (Exception e) {
					throw processException(e);
				}
				finally {
					closeSession(session);
				}
			}

			protected ${entity.name} filterGetBy${entityFinder.name}_PrevAndNext(
				Session session, ${entity.name} ${entity.varName},

				<#list entityColumns as entityColumn>
					${entityColumn.type} ${entityColumn.name},
				</#list>

				OrderByComparator<${entity.name}> orderByComparator, boolean previous) {

				<#if entity.isPermissionedModel()>
					<#include "persistence_impl_get_by_prev_and_next_query.ftl">

					String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN<#if entityFinder.hasEntityColumn("groupId")>, groupId</#if>);

					Query q = session.createQuery(sql);

					q.setFirstResult(0);
					q.setMaxResults(2);

					QueryPos qPos = QueryPos.getInstance(q);

					<@finderQPos />

					if (orderByComparator != null) {
						for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(${entity.varName})) {
							qPos.add(orderByConditionValue);
						}
					}

					List<${entity.name}> list = q.list();

					if (list.size() == 2) {
						return list.get(1);
					}
					else {
						return null;
					}
				<#else>
					StringBundler query = null;

					if (orderByComparator != null) {
						query = new StringBundler(${entityColumns?size + 4} + (orderByComparator.getOrderByConditionFields().length * 3) + (orderByComparator.getOrderByFields().length * 3));
					}
					else {
						query = new StringBundler(${entityColumns?size + 3});
					}

					if (getDB().isSupportsInlineDistinct()) {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE);
					}
					else {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1);
					}

					<#assign sqlQuery = true />

					<#include "persistence_impl_finder_cols.ftl">

					<#assign sqlQuery = false />

					if (!getDB().isSupportsInlineDistinct()) {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2);
					}

					if (orderByComparator != null) {
						String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

						if (orderByConditionFields.length > 0) {
							query.append(WHERE_AND);
						}

						for (int i = 0; i < orderByConditionFields.length; i++) {
							if (getDB().isSupportsInlineDistinct()) {
								query.append(getColumnName(_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i], true));
							}
							else {
								query.append(getColumnName(_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i], true));
							}

							if ((i + 1) < orderByConditionFields.length) {
								if (orderByComparator.isAscending() ^ previous) {
									query.append(WHERE_GREATER_THAN_HAS_NEXT);
								}
								else {
									query.append(WHERE_LESSER_THAN_HAS_NEXT);
								}
							}
							else {
								if (orderByComparator.isAscending() ^ previous) {
									query.append(WHERE_GREATER_THAN);
								}
								else {
									query.append(WHERE_LESSER_THAN);
								}
							}
						}

						query.append(ORDER_BY_CLAUSE);

						String[] orderByFields = orderByComparator.getOrderByFields();

						for (int i = 0; i < orderByFields.length; i++) {
							if (getDB().isSupportsInlineDistinct()) {
								query.append(getColumnName(_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
							}
							else {
								query.append(getColumnName(_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
							}

							if ((i + 1) < orderByFields.length) {
								if (orderByComparator.isAscending() ^ previous) {
									query.append(ORDER_BY_ASC_HAS_NEXT);
								}
								else {
									query.append(ORDER_BY_DESC_HAS_NEXT);
								}
							}
							else {
								if (orderByComparator.isAscending() ^ previous) {
									query.append(ORDER_BY_ASC);
								}
								else {
									query.append(ORDER_BY_DESC);
								}
							}
						}
					}
					else {
						if (getDB().isSupportsInlineDistinct()) {
							query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
						}
						else {
							query.append(${entity.name}ModelImpl.ORDER_BY_SQL);
						}
					}

					String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(), ${entity.name}.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN<#if entityFinder.hasEntityColumn("groupId")>, groupId</#if>);

					SQLQuery q = session.createSynchronizedSQLQuery(sql);

					q.setFirstResult(0);
					q.setMaxResults(2);

					if (getDB().isSupportsInlineDistinct()) {
						q.addEntity(_FILTER_ENTITY_ALIAS, ${entity.name}Impl.class);
					}
					else {
						q.addEntity(_FILTER_ENTITY_TABLE, ${entity.name}Impl.class);
					}

					QueryPos qPos = QueryPos.getInstance(q);

					<@finderQPos />

					if (orderByComparator != null) {
						for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(${entity.varName})) {
							qPos.add(orderByConditionValue);
						}
					}

					List<${entity.name}> list = q.list();

					if (list.size() == 2) {
						return list.get(1);
					}
					else {
						return null;
					}
				</#if>
			}
		</#if>

		<#if entityFinder.hasArrayableOperator()>
			/**
			 * Returns all the ${entity.humanNames} that the user has permission to view where ${entityFinder.getHumanConditions(true)}.
			 *
			<#list entityColumns as entityColumn>
				<#if entityColumn.hasArrayableOperator()>
			 * @param ${entityColumn.names} the ${entityColumn.humanNames}
				<#else>
			 * @param ${entityColumn.name} the ${entityColumn.humanName}
				</#if>
			</#list>
			 * @return the matching ${entity.humanNames} that the user has permission to view
			 */
			@Override
			public List<${entity.name}> filterFindBy${entityFinder.name}(

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
				return filterFindBy${entityFinder.name}(

				<#list entityColumns as entityColumn>
					<#if entityColumn.hasArrayableOperator()>
						${entityColumn.names},
					<#else>
						${entityColumn.name},
					</#if>
				</#list>

				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
			}

			/**
			 * Returns a range of all the ${entity.humanNames} that the user has permission to view where ${entityFinder.getHumanConditions(true)}.
			 *
			 * <p>
			 * <#include "range_comment.ftl">
			 * </p>
			 *
			<#list entityColumns as entityColumn>
				<#if entityColumn.hasArrayableOperator()>
			 * @param ${entityColumn.names} the ${entityColumn.humanNames}
				<#else>
			 * @param ${entityColumn.name} the ${entityColumn.humanName}
				</#if>
			</#list>
			 * @param start the lower bound of the range of ${entity.humanNames}
			 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
			 * @return the range of matching ${entity.humanNames} that the user has permission to view
			 */
			@Override
			public List<${entity.name}> filterFindBy${entityFinder.name}(

			<#list entityColumns as entityColumn>
				<#if entityColumn.hasArrayableOperator()>
					${entityColumn.type}[] ${entityColumn.names},
				<#else>
					${entityColumn.type} ${entityColumn.name},
				</#if>
			</#list>

			int start, int end) {
				return filterFindBy${entityFinder.name}(

				<#list entityColumns as entityColumn>
					<#if entityColumn.hasArrayableOperator()>
						${entityColumn.names},
					<#else>
						${entityColumn.name},
					</#if>
				</#list>

				start, end, null);
			}

			/**
			 * Returns an ordered range of all the ${entity.humanNames} that the user has permission to view where ${entityFinder.getHumanConditions(true)}.
			 *
			 * <p>
			 * <#include "range_comment.ftl">
			 * </p>
			 *
			<#list entityColumns as entityColumn>
				<#if entityColumn.hasArrayableOperator()>
			 * @param ${entityColumn.names} the ${entityColumn.humanNames}
				<#else>
			 * @param ${entityColumn.name} the ${entityColumn.humanName}
				</#if>
			</#list>
			 * @param start the lower bound of the range of ${entity.humanNames}
			 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
			 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
			 * @return the ordered range of matching ${entity.humanNames} that the user has permission to view
			 */
			@Override
			public List<${entity.name}> filterFindBy${entityFinder.name}(

			<#list entityColumns as entityColumn>
				<#if entityColumn.hasArrayableOperator()>
					${entityColumn.type}[] ${entityColumn.names},
				<#else>
					${entityColumn.type} ${entityColumn.name},
				</#if>
			</#list>

			int start, int end, OrderByComparator<${entity.name}> orderByComparator) {
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

					return findBy${entityFinder.name}(

					<#list entityColumns as entityColumn>
						<#if entityColumn.hasArrayableOperator()>
							${entityColumn.names},
						<#else>
							${entityColumn.name},
						</#if>
					</#list>

					start, end, orderByComparator);
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
					<#include "persistence_impl_find_by_arrayable_query.ftl">

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

						return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
					}
					catch (Exception e) {
						throw processException(e);
					}
					finally {
						closeSession(session);
					}
				<#else>
					StringBundler query = new StringBundler();

					if (getDB().isSupportsInlineDistinct()) {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_WHERE);
					}
					else {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_1);
					}

					<#assign sqlQuery = true />

					<#include "persistence_impl_finder_arrayable_cols.ftl">

					<#assign sqlQuery = false />

					if (!getDB().isSupportsInlineDistinct()) {
						query.append(_FILTER_SQL_SELECT_${entity.alias?upper_case}_NO_INLINE_DISTINCT_WHERE_2);
					}

					if (orderByComparator != null) {
						if (getDB().isSupportsInlineDistinct()) {
							appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
						}
						else {
							appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
						}
					}
					else {
						if (getDB().isSupportsInlineDistinct()) {
							query.append(${entity.name}ModelImpl.ORDER_BY_JPQL);
						}
						else {
							query.append(${entity.name}ModelImpl.ORDER_BY_SQL);
						}
					}

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

						if (getDB().isSupportsInlineDistinct()) {
							q.addEntity(_FILTER_ENTITY_ALIAS, ${entity.name}Impl.class);
						}
						else {
							q.addEntity(_FILTER_ENTITY_TABLE, ${entity.name}Impl.class);
						}

						<#if bindParameter(entityColumns)>
							QueryPos qPos = QueryPos.getInstance(q);
						</#if>

						<@finderQPos _arrayable=true />

						return (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
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
</#if>

<#-- Case 4: !entityFinder.isCollection() && !entityFinder.isUnique() -->

<#if !entityFinder.isCollection() && !entityFinder.isUnique()>
</#if>

<#-- Case 5: entityFinder.isUnique() -->

<#if entityFinder.isUnique()>
</#if>

<#-- Case 6: !entityFinder.isUnique() -->

<#if !entityFinder.isUnique()>
</#if>

<#-- Case 7: entityFinder.isCollection() && entityFinder.hasArrayableOperator() && !entityFinder.hasArrayablePagination() -->

<#if entityFinder.isCollection() && entityFinder.hasArrayableOperator() && !entityFinder.hasArrayablePagination()>
	/**
	 * Returns all the ${entity.humanNames} where ${entityFinder.getHumanConditions(true)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
	 * @param ${entityColumn.names} the ${entityColumn.humanNames}
		<#else>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#if>
	</#list>
	 * @return the matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

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
		return findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				${entityColumn.names},
			<#else>
				${entityColumn.name},
			</#if>
		</#list>

		QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ${entity.humanNames} where ${entityFinder.getHumanConditions(true)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
	 * @param ${entityColumn.names} the ${entityColumn.humanNames}
		<#else>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#if>
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @return the range of matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
			${entityColumn.type}[] ${entityColumn.names},
		<#else>
			${entityColumn.type} ${entityColumn.name},
		</#if>
	</#list>

	int start, int end) {
		return findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				${entityColumn.names},
			<#else>
				${entityColumn.name},
			</#if>
		</#list>

		start, end, null);
	}

	/**
	 * Returns an ordered range of all the ${entity.humanNames} where ${entityFinder.getHumanConditions(true)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
	 * @param ${entityColumn.names} the ${entityColumn.humanNames}
		<#else>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#if>
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
			${entityColumn.type}[] ${entityColumn.names},
		<#else>
			${entityColumn.type} ${entityColumn.name},
		</#if>
	</#list>

	int start, int end, OrderByComparator<${entity.name}> orderByComparator) {
		return findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				${entityColumn.names},
			<#else>
				${entityColumn.name},
			</#if>
		</#list>

		start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ${entity.humanNames} where ${entityFinder.getHumanConditions(false)}, optionally using the finder cache.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
			${entityColumn.type}[] ${entityColumn.names},
		<#else>
			${entityColumn.type} ${entityColumn.name},
		</#if>
	</#list>

	int start, int end, OrderByComparator<${entity.name}> orderByComparator, boolean useFinderCache) {
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

		if (
		<#assign firstCol = true />
		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				<#if firstCol>
					<#assign firstCol = false />
				<#else>
					&&
				</#if>

				${entityColumn.names}.length == 1
			</#if>
		</#list>
		) {
			<#if entityFinder.isUnique()>
				${entity.name} ${entity.varName} = fetchBy${entityFinder.name}(
					<#list entityColumns as entityColumn>
						<#if entityColumn.hasArrayableOperator()>
							${entityColumn.names}[0]
						<#else>
							${entityColumn.name}
						</#if>

						<#if entityColumn_has_next>
							,
						</#if>
					</#list>);

				if (${entity.varName} == null) {
					return Collections.emptyList();
				}
				else {
					List<${entity.name}> list = new ArrayList<${entity.name}>(1);

					list.add(${entity.varName});

					return list;
				}
			<#else>
				return findBy${entityFinder.name}(
					<#list entityColumns as entityColumn>
						<#if entityColumn.hasArrayableOperator()>
							${entityColumn.names}[0],
						<#else>
							${entityColumn.name},
						</#if>
					</#list>

					start, end, orderByComparator);
			</#if>
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (orderByComparator == null)) {
			pagination = false;
			if (useFinderCache) {
				finderArgs = new Object[] {
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
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				<#list entityColumns as entityColumn>
					<#if entityColumn.hasArrayableOperator()>
						StringUtil.merge(${entityColumn.names}),
					<#elseif stringUtil.equals(entityColumn.type, "Date")>
						_getTime(${entityColumn.name}),
					<#else>
						${entityColumn.name},
					</#if>
				</#list>

				start, end, orderByComparator
			};
		}

		List<${entity.name}> list = null;

		if (useFinderCache) {
			list = (List<${entity.name}>)${finderCache}.getResult(_finderPathWithPaginationFindBy${entityFinder.name}, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (${entity.name} ${entity.varName} : list) {
					if (
						<#list entityColumns as entityColumn>
							<#if entityColumn.hasArrayableOperator()>
								!ArrayUtil.contains(${entityColumn.names}, ${entity.varName}.get${entityColumn.methodName}())
							<#else>
								<#include "persistence_impl_finder_field_comparator.ftl">
							</#if>

							<#if entityColumn_has_next>
								||
							</#if>
						</#list>
					) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			<#assign checkPagination = true />

			<#include "persistence_impl_find_by_arrayable_query.ftl">

			<#assign checkPagination = false />

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				<#if bindParameter(entityColumns)>
					QueryPos qPos = QueryPos.getInstance(q);
				</#if>

				<@finderQPos _arrayable=true />

				if (!pagination) {
					list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					${finderCache}.putResult(_finderPathWithPaginationFindBy${entityFinder.name}, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					${finderCache}.removeResult(_finderPathWithPaginationFindBy${entityFinder.name}, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}
</#if>

<#-- Case 7.1: entityFinder.isCollection() && entityFinder.hasArrayableOperator() && entityFinder.hasArrayablePagination() -->

<#if entityFinder.isCollection() && entityFinder.hasArrayableOperator() && entityFinder.hasArrayablePagination()>
	/**
	 * Returns all the ${entity.humanNames} where ${entityFinder.getHumanConditions(true)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
	 * @param ${entityColumn.names} the ${entityColumn.humanNames}
		<#else>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#if>
	</#list>
	 * @return the matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

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
		return findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				${entityColumn.names},
			<#else>
				${entityColumn.name},
			</#if>
		</#list>

		QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ${entity.humanNames} where ${entityFinder.getHumanConditions(true)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
	 * @param ${entityColumn.names} the ${entityColumn.humanNames}
		<#else>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#if>
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @return the range of matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
			${entityColumn.type}[] ${entityColumn.names},
		<#else>
			${entityColumn.type} ${entityColumn.name},
		</#if>
	</#list>

	int start, int end) {
		return findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				${entityColumn.names},
			<#else>
				${entityColumn.name},
			</#if>
		</#list>

		start, end, null);
	}

	/**
	 * Returns an ordered range of all the ${entity.humanNames} where ${entityFinder.getHumanConditions(true)}.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
	 * @param ${entityColumn.names} the ${entityColumn.humanNames}
		<#else>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
		</#if>
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
			${entityColumn.type}[] ${entityColumn.names},
		<#else>
			${entityColumn.type} ${entityColumn.name},
		</#if>
	</#list>

	int start, int end, OrderByComparator<${entity.name}> orderByComparator) {
		return findBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				${entityColumn.names},
			<#else>
				${entityColumn.name},
			</#if>
		</#list>

		start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ${entity.humanNames} where ${entityFinder.getHumanConditions(false)}, optionally using the finder cache.
	 *
	 * <p>
	 * <#include "range_comment.ftl">
	 * </p>
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @param start the lower bound of the range of ${entity.humanNames}
	 * @param end the upper bound of the range of ${entity.humanNames} (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ${entity.humanNames}
	 */
	@Override
	public List<${entity.name}> findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
			${entityColumn.type}[] ${entityColumn.names},
		<#else>
			${entityColumn.type} ${entityColumn.name},
		</#if>
	</#list>

	int start, int end, OrderByComparator<${entity.name}> orderByComparator, boolean useFinderCache) {
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

		if (
		<#assign firstCol = true />
		<#list entityColumns as entityColumn>
			<#if entityColumn.hasArrayableOperator()>
				<#if firstCol>
					<#assign firstCol = false />
				<#else>
					&&
				</#if>

				${entityColumn.names}.length == 1
			</#if>
		</#list>
		) {
			<#if entityFinder.isUnique()>
				${entity.name} ${entity.varName} = fetchBy${entityFinder.name}(
					<#list entityColumns as entityColumn>
						<#if entityColumn.hasArrayableOperator()>
							${entityColumn.names}[0]
						<#else>
							${entityColumn.name}
						</#if>

						<#if entityColumn_has_next>
							,
						</#if>
					</#list>);

				if (${entity.varName} == null) {
					return Collections.emptyList();
				}
				else {
					return Collections.singletonList(${entity.varName});
				}
			<#else>
				return findBy${entityFinder.name}(
					<#list entityColumns as entityColumn>
						<#if entityColumn.hasArrayableOperator()>
							${entityColumn.names}[0],
						<#else>
							${entityColumn.name},
						</#if>
					</#list>

					start, end, orderByComparator);
			</#if>
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (orderByComparator == null)) {
			pagination = false;

			if (useFinderCache) {
				finderArgs = new Object[] {
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
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				<#list entityColumns as entityColumn>
					<#if entityColumn.hasArrayableOperator()>
						StringUtil.merge(${entityColumn.names}),
					<#else>
						${entityColumn.name},
					</#if>
				</#list>

				start, end, orderByComparator
			};
		}

		List<${entity.name}> list = null;

		if (useFinderCache) {
			list = (List<${entity.name}>)${finderCache}.getResult(_finderPathWithPaginationFindBy${entityFinder.name}, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (${entity.name} ${entity.varName} : list) {
					if (
						<#list entityColumns as entityColumn>
							<#if entityColumn.hasArrayableOperator()>
								!ArrayUtil.contains(${entityColumn.names}, ${entity.varName}.get${entityColumn.methodName}())
							<#else>
								<#include "persistence_impl_finder_field_comparator.ftl">
							</#if>

							<#if entityColumn_has_next>
								||
							</#if>
						</#list>
					) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			try {
				if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) && (databaseInMaxParameters > 0) && (<#list entityFinderArrayableColsList as arrayableentityColumn>
						(${arrayableentityColumn.names}.length > databaseInMaxParameters)

						<#if arrayableentityColumn_has_next>
							||
						</#if>
					</#list>)) {

					list = new ArrayList<${entity.name}>();

					<#list entityFinderArrayableColsList as arrayableentityColumn>
						${arrayableentityColumn.type}[][] ${arrayableentityColumn.names}Pages = (${arrayableentityColumn.type}[][])ArrayUtil.split(${arrayableentityColumn.names}, databaseInMaxParameters);
					</#list>

					<#list entityFinderArrayableColsList as arrayableentityColumn>
						for (${arrayableentityColumn.type}[] ${arrayableentityColumn.names}Page : ${arrayableentityColumn.names}Pages) {
					</#list>

						list.addAll(_findBy${entityFinder.name}(

						<#list entityColumns as entityColumn>
							<#if entityColumn.hasArrayableOperator()>
								${entityColumn.names}Page,
							<#else>
								${entityColumn.name},
							</#if>
						</#list>

						start, end, orderByComparator, pagination));
					<#list entityFinderArrayableColsList as arrayableentityColumn>
						}
					</#list>

					Collections.sort(list, orderByComparator);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = _findBy${entityFinder.name}(

					<#list entityColumns as entityColumn>
						<#if entityColumn.hasArrayableOperator()>
							${entityColumn.names},
						<#else>
							${entityColumn.name},
						</#if>
					</#list>

					start, end, orderByComparator, pagination);
				}

				cacheResult(list);

				if (useFinderCache) {
					${finderCache}.putResult(_finderPathWithPaginationFindBy${entityFinder.name}, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					${finderCache}.removeResult(_finderPathWithPaginationFindBy${entityFinder.name}, finderArgs);
				}

				throw processException(e);
			}
		}

		return list;
	}

	private List<${entity.name}> _findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		<#if entityColumn.hasArrayableOperator()>
			${entityColumn.type}[] ${entityColumn.names},
		<#else>
			${entityColumn.type} ${entityColumn.name},
		</#if>
	</#list>

	int start, int end, OrderByComparator<${entity.name}> orderByComparator, boolean pagination) {
		List<${entity.name}> list = null;

		<#assign checkPagination = true />

		<#include "persistence_impl_find_by_arrayable_query.ftl">

		<#assign checkPagination = false />

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			<#if bindParameter(entityColumns)>
				QueryPos qPos = QueryPos.getInstance(q);
			</#if>

			<@finderQPos _arrayable=true />

			if (!pagination) {
				list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end, false);

				Collections.sort(list);

				list = Collections.unmodifiableList(list);
			}
			else {
				list = (List<${entity.name}>)QueryUtil.list(q, getDialect(), start, end);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return list;
	}
</#if>

<#-- Case 8: !entityFinder.isCollection() -->

<#if !entityFinder.isCollection()>
</#if>

<#-- Case 9: !entityFinder.isCollection() || entityFinder.isUnique() -->

<#if !entityFinder.isCollection() || entityFinder.isUnique()>
	/**
	 * Returns the ${entity.humanName} where ${entityFinder.getHumanConditions(false)} or throws a <code>${noSuchEntity}Exception</code> if it could not be found.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @return the matching ${entity.humanName}
	 * @throws ${noSuchEntity}Exception if a matching ${entity.humanName} could not be found
	 */
	@Override
	public ${entity.name} findBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name}

		<#if entityColumn_has_next>
			,
		</#if>
	</#list>

	) throws ${noSuchEntity}Exception {
		${entity.name} ${entity.varName} = fetchBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name}

			<#if entityColumn_has_next>
				,
			</#if>
		</#list>

		);

		if ( ${entity.varName} == null) {
			StringBundler msg = new StringBundler(${(entityColumns?size * 2) + 2});

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			<#list entityColumns as entityColumn>
				msg.append("<#if entityColumn_index != 0>, </#if>${entityColumn.name}=");
				msg.append(${entityColumn.name});

				<#if !entityColumn_has_next>
					msg.append("}");
				</#if>
			</#list>

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new ${noSuchEntity}Exception(msg.toString());
		}

		return ${entity.varName};
	}

	/**
	 * Returns the ${entity.humanName} where ${entityFinder.getHumanConditions(false)} or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @return the matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
	 */
	@Override
	public ${entity.name} fetchBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name}

		<#if entityColumn_has_next>
			,
		</#if>
	</#list>

	) {
		return fetchBy${entityFinder.name}(

		<#list entityColumns as entityColumn>
			${entityColumn.name},
		</#list>

		true);
	}

	/**
	 * Returns the ${entity.humanName} where ${entityFinder.getHumanConditions(false)} or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	<#list entityColumns as entityColumn>
	 * @param ${entityColumn.name} the ${entityColumn.humanName}
	</#list>
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ${entity.humanName}, or <code>null</code> if a matching ${entity.humanName} could not be found
	 */
	@Override
	public ${entity.name} fetchBy${entityFinder.name}(

	<#list entityColumns as entityColumn>
		${entityColumn.type} ${entityColumn.name}

		,
	</#list>

	boolean useFinderCache) {
		<#list entityColumns as entityColumn>
			<#if stringUtil.equals(entityColumn.type, "String") && entityColumn.isConvertNull()>
				${entityColumn.name} = Objects.toString(${entityColumn.name}, "");
			</#if>
		</#list>

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
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
		}

		Object result = null;

		if (useFinderCache) {
			result = ${finderCache}.getResult(_finderPathFetchBy${entityFinder.name}, finderArgs, this);
		}

		if (result instanceof ${entity.name}) {
			${entity.name} ${entity.varName} = (${entity.name})result;

			if (
				<#list entityColumns as entityColumn>
					<#if entityColumn.isPrimitiveType(false)>
						<#if stringUtil.equals(entityColumn.type, "boolean")>
							(${entityColumn.name} != ${entity.varName}.is${entityColumn.methodName}())
						<#else>
							(${entityColumn.name} != ${entity.varName}.get${entityColumn.methodName}())
						</#if>
					<#else>
						!Objects.equals(${entityColumn.name}, ${entity.varName}.get${entityColumn.methodName}())
					</#if>

					<#if entityColumn_has_next>
						||
					</#if>
				</#list>
			) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(${entityColumns?size + 2});

			query.append(_SQL_SELECT_${entity.alias?upper_case}_WHERE);

			<#include "persistence_impl_finder_cols.ftl">

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				<@finderQPos />

				List<${entity.name}> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						${finderCache}.putResult(_finderPathFetchBy${entityFinder.name}, finderArgs, list);
					}
				}
				else {
					<#if !entityFinder.isUnique()>
						if (list.size() > 1) {
							Collections.sort(list, Collections.reverseOrder());

							if (_log.isWarnEnabled()) {
								if (!useFinderCache) {
									finderArgs = new Object[] {
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
								}

								_log.warn("${entity.name}PersistenceImpl.fetchBy${entityFinder.name}(<#list entityColumns as entityColumn>${entityColumn.type}, </#list>boolean) with parameters (" + StringUtil.merge(finderArgs) + ") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
							}
						}
					</#if>

					${entity.name} ${entity.varName} = list.get(0);

					result = ${entity.varName};

					cacheResult(${entity.varName});
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					${finderCache}.removeResult(_finderPathFetchBy${entityFinder.name}, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (${entity.name})result;
		}
	}
</#if>