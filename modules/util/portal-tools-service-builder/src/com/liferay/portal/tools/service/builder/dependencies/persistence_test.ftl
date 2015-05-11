<#assign parentPKColumn = "">

<#if entity.isHierarchicalTree()>
	<#if entity.hasColumn("groupId")>
		<#assign scopeColumn = entity.getColumn("groupId")>
	<#else>
		<#assign scopeColumn = entity.getColumn("companyId")>
	</#if>

	<#assign pkColumn = entity.getPKList()?first>

	<#assign parentPKColumn = entity.getColumn("parent" + pkColumn.methodName)>
</#if>

package ${packagePath}.service.persistence.test;

<#assign noSuchEntity = serviceBuilder.getNoSuchEntityException(entity)>

<#if osgiModule>
	import ${packagePath}.exception.${noSuchEntity}Exception;
<#else>
	import ${packagePath}.${noSuchEntity}Exception;
</#if>

import ${packagePath}.model.${entity.name};
import ${packagePath}.service.${entity.name}LocalServiceUtil;
import ${packagePath}.service.persistence.${entity.name}PK;
import ${packagePath}.service.persistence.${entity.name}Persistence;
import ${packagePath}.service.persistence.${entity.name}Util;

import ${beanLocatorUtil};

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
<#if osgiModule>
	@RunWith(Arquillian.class)
</#if>
public class ${entity.name}PersistenceTest {

	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE, new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = ${entity.name}Util.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<${entity.name}> iterator = _${entity.varNames}.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				RandomTestUtil.nextInt()
			<#elseif column.type == "long">
				RandomTestUtil.nextLong()
			<#elseif column.type == "String">
				RandomTestUtil.randomString()
			</#if>

			;
		</#if>

		${entity.name} ${entity.varName} = _persistence.create(pk);

		Assert.assertNotNull(${entity.varName});

		Assert.assertEquals(${entity.varName}.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		_persistence.remove(new${entity.name});

		${entity.name} existing${entity.name} = _persistence.fetchByPrimaryKey(new${entity.name}.getPrimaryKey());

		Assert.assertNull(existing${entity.name});
	}

	@Test
	public void testUpdateNew() throws Exception {
		add${entity.name}();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				RandomTestUtil.nextInt()
			<#elseif column.type == "long">
				RandomTestUtil.nextLong()
			<#elseif column.type == "String">
				RandomTestUtil.randomString()
			</#if>

			;
		</#if>

		${entity.name} new${entity.name} = _persistence.create(pk);

		<#list entity.regularColList as column>
			<#if !column.primary && ((parentPKColumn == "") || (parentPKColumn.name != column.name))>
				<#if column.type == "Blob">
					String new${column.methodName}String = RandomTestUtil.randomString();

					byte[] new${column.methodName}Bytes = new${column.methodName}String.getBytes(StringPool.UTF8);

					Blob new${column.methodName}Blob = new OutputBlob(new UnsyncByteArrayInputStream(new${column.methodName}Bytes), new${column.methodName}Bytes.length);
				</#if>

				new${entity.name}.set${column.methodName}(

				<#if column.type == "boolean">
					RandomTestUtil.randomBoolean()
				<#elseif column.type == "double">
					RandomTestUtil.nextDouble()
				<#elseif column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "Date">
					RandomTestUtil.nextDate()
				<#elseif column.type == "Blob">
					new${column.methodName}Blob
				<#elseif column.type == "Map">
					new HashMap<String, Serializable>()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				);
			</#if>
		</#list>

		_${entity.varNames}.add(_persistence.update(new${entity.name}));

		${entity.name} existing${entity.name} = _persistence.findByPrimaryKey(new${entity.name}.getPrimaryKey());

		<#list entity.regularColList as column>
			<#if column.type == "Blob">
				Blob existing${column.methodName} = existing${entity.name}.get${column.methodName}();

				Assert.assertTrue(Arrays.equals(existing${column.methodName}.getBytes(1, (int)existing${column.methodName}.length()), new${column.methodName}Bytes));
			<#elseif column.type == "Date">
				Assert.assertEquals(Time.getShortTimestamp(existing${entity.name}.get${column.methodName}()), Time.getShortTimestamp(new${entity.name}.get${column.methodName}()));
			<#elseif column.type == "double">
				AssertUtils.assertEquals(existing${entity.name}.get${column.methodName}(), new${entity.name}.get${column.methodName}());
			<#else>
				Assert.assertEquals(existing${entity.name}.get${column.methodName}(), new${entity.name}.get${column.methodName}());
			</#if>
		</#list>
	}

	<#list entity.getFinderList() as finder>
		@Test
		public void testCountBy${finder.name}() throws Exception {
			_persistence.countBy${finder.name}(

			<#assign hasString = false>

			<#list finder.getColumns() as finderCol>
				<#if finderCol.type == "boolean">
					RandomTestUtil.randomBoolean()
				<#elseif finderCol.type == "double">
					RandomTestUtil.nextDouble()
				<#elseif finderCol.type == "int">
					RandomTestUtil.nextInt()
				<#elseif finderCol.type == "long">
					RandomTestUtil.nextLong()
				<#elseif finderCol.type == "Date">
					RandomTestUtil.nextDate()
				<#elseif finderCol.type == "String">
					<#assign hasString = true>

					StringPool.BLANK
				<#else>
					(${finderCol.type})null
				</#if>

				<#if finderCol_has_next >
					,
				</#if>
			</#list>

			);

			<#if hasString>
				_persistence.countBy${finder.name}(

					<#list finder.getColumns() as finderCol>
						<#if finderCol.type == "boolean">
							RandomTestUtil.randomBoolean()
						<#elseif finderCol.type == "double">
							0D
						<#elseif finderCol.type == "int">
							0
						<#elseif finderCol.type == "long">
							0L
						<#elseif finderCol.type == "Date">
							RandomTestUtil.nextDate()
						<#elseif finderCol.type == "String">
							StringPool.NULL
						<#else>
							(${finderCol.type})null
						</#if>

						<#if finderCol_has_next >
							,
						</#if>
					</#list>

				);
			</#if>

			_persistence.countBy${finder.name}(

				<#list finder.getColumns() as finderCol>
					<#if finderCol.type == "boolean">
						RandomTestUtil.randomBoolean()
					<#elseif finderCol.type == "double">
						0D
					<#elseif finderCol.type == "int">
						0
					<#elseif finderCol.type == "long">
						0L
					<#elseif finderCol.type == "Date">
						RandomTestUtil.nextDate()
					<#else>
						(${finderCol.type})null
					</#if>

					<#if finderCol_has_next >
						,
					</#if>
				</#list>

			);
		}

		<#if finder.hasArrayableOperator()>
			@Test
			public void testCountBy${finder.name}Arrayable() throws Exception {
				_persistence.countBy${finder.name}(

				<#list finder.getColumns() as finderCol>
					<#if finderCol.hasArrayableOperator()>
						new ${finderCol.type}[]{

						<#if finderCol.type == "boolean">
							RandomTestUtil.randomBoolean()
						<#elseif finderCol.type == "double">
							RandomTestUtil.nextDouble(), 0D
						<#elseif finderCol.type == "int">
							RandomTestUtil.nextInt(), 0
						<#elseif finderCol.type == "long">
							RandomTestUtil.nextLong(), 0L
						<#elseif finderCol.type == "Date">
							RandomTestUtil.nextDate(), null
						<#elseif finderCol.type == "String">
							RandomTestUtil.randomString(), StringPool.BLANK, StringPool.NULL, null, null
						<#else>
							null
						</#if>
					<#else>
						<#if finderCol.type == "boolean">
							RandomTestUtil.randomBoolean()
						<#elseif finderCol.type == "double">
							RandomTestUtil.nextDouble()
						<#elseif finderCol.type == "int">
							RandomTestUtil.nextInt()
						<#elseif finderCol.type == "long">
							RandomTestUtil.nextLong()
						<#elseif finderCol.type == "Date">
							RandomTestUtil.nextDate()
						<#elseif finderCol.type == "String">
							RandomTestUtil.randomString()
						<#else>
							null
						</#if>
					</#if>

					<#if finderCol.hasArrayableOperator()>
						}
					</#if>

					<#if finderCol_has_next>
						,
					</#if>
				</#list>

				);
			}
		</#if>
	</#list>

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		${entity.name} existing${entity.name} = _persistence.findByPrimaryKey(new${entity.name}.getPrimaryKey());

		Assert.assertEquals(existing${entity.name}, new${entity.name});
	}

	@Test(expected = ${noSuchEntity}Exception.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				RandomTestUtil.nextInt()
			<#elseif column.type == "long">
				RandomTestUtil.nextLong()
			<#elseif column.type == "String">
				RandomTestUtil.randomString()
			</#if>

			;
		</#if>

		_persistence.findByPrimaryKey(pk);
	}

	<#if !entity.hasCompoundPK()>
		@Test
		public void testFindAll() throws Exception {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
		}

		<#list entity.getFinderList() as finder>
			<#if (finder.name == "GroupId") && entity.isPermissionCheckEnabled(finder)>
				@Test
				public void testFilterFindByGroupId() throws Exception {
					_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
				}

				<#break>
			</#if>
		</#list>

		protected OrderByComparator<${entity.name}> getOrderByComparator() {
			return OrderByComparatorFactoryUtil.create(
				"${entity.table}",

				<#list entity.regularColList as column>
					<#if column.type != "Blob">
						"${column.name}", true

						<#if column_has_next>
							,
						</#if>
					</#if>
				</#list>

				);
		}
	</#if>

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		${entity.name} existing${entity.name} = _persistence.fetchByPrimaryKey(new${entity.name}.getPrimaryKey());

		Assert.assertEquals(existing${entity.name}, new${entity.name});
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				RandomTestUtil.nextInt()
			<#elseif column.type == "long">
				RandomTestUtil.nextLong()
			<#elseif column.type == "String">
				RandomTestUtil.randomString()
			</#if>

			;
		</#if>

		${entity.name} missing${entity.name} = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missing${entity.name});
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist() throws Exception {
		${entity.name} new${entity.name}1 = add${entity.name}();
		${entity.name} new${entity.name}2 = add${entity.name}();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(new${entity.name}1.getPrimaryKey());
		primaryKeys.add(new${entity.name}2.getPrimaryKey());

		Map<Serializable, ${entity.name}> ${entity.varNames} = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ${entity.varNames}.size());
		Assert.assertEquals(new${entity.name}1, ${entity.varNames}.get(new${entity.name}1.getPrimaryKey()));
		Assert.assertEquals(new${entity.name}2, ${entity.varNames}.get(new${entity.name}2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk1 = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);

			${entity.PKClassName} pk2 = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk1 =

			<#if column.type == "int">
				RandomTestUtil.nextInt()
			<#elseif column.type == "long">
				RandomTestUtil.nextLong()
			<#elseif column.type == "String">
				RandomTestUtil.randomString()
			</#if>

			;

			${column.type} pk2 =

			<#if column.type == "int">
				RandomTestUtil.nextInt()
			<#elseif column.type == "long">
				RandomTestUtil.nextLong()
			<#elseif column.type == "String">
				RandomTestUtil.randomString()
			</#if>

			;
		</#if>

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, ${entity.name}> ${entity.varNames} = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(${entity.varNames}.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			${column.type} pk =

			<#if column.type == "int">
				RandomTestUtil.nextInt()
			<#elseif column.type == "long">
				RandomTestUtil.nextLong()
			<#elseif column.type == "String">
				RandomTestUtil.randomString()
			</#if>

			;
		</#if>

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(new${entity.name}.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, ${entity.name}> ${entity.varNames} = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ${entity.varNames}.size());
		Assert.assertEquals(new${entity.name}, ${entity.varNames}.get(new${entity.name}.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, ${entity.name}> ${entity.varNames} = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(${entity.varNames}.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(new${entity.name}.getPrimaryKey());

		Map<Serializable, ${entity.name}> ${entity.varNames} = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ${entity.varNames}.size());
		Assert.assertEquals(new${entity.name}, ${entity.varNames}.get(new${entity.name}.getPrimaryKey()));
	}

	<#if entity.hasActionableDynamicQuery()>
		@Test
		public void testActionableDynamicQuery() throws Exception {
			final IntegerWrapper count = new IntegerWrapper();

			ActionableDynamicQuery actionableDynamicQuery = ${entity.name}LocalServiceUtil.getActionableDynamicQuery();

			actionableDynamicQuery.setPerformActionMethod(
				new ActionableDynamicQuery.PerformActionMethod() {

					@Override
					public void performAction(Object object) {
						${entity.name} ${entity.varName} = (${entity.name})object;

						Assert.assertNotNull(${entity.varName});

						count.increment();
					}

				});

			actionableDynamicQuery.performActions();

			Assert.assertEquals(count.getValue(), _persistence.countAll());
		}
	</#if>

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, _dynamicQueryClassLoader);

		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				dynamicQuery.add(RestrictionsFactoryUtil.eq("id.${column.name}", new${entity.name}.get${column.methodName}()));
			</#list>
		<#else>
			<#assign column = entity.PKList[0]>

			dynamicQuery.add(RestrictionsFactoryUtil.eq("${column.name}", new${entity.name}.get${column.methodName}()));
		</#if>

		List<${entity.name}> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		${entity.name} existing${entity.name} = result.get(0);

		Assert.assertEquals(existing${entity.name}, new${entity.name});
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, _dynamicQueryClassLoader);

		<#if entity.hasCompoundPK()>
			<#list entity.PKList as column>
				dynamicQuery.add(RestrictionsFactoryUtil.eq("id.${column.name}",

				<#if column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				));
			</#list>
		<#else>
			<#assign column = entity.PKList[0]>

			dynamicQuery.add(RestrictionsFactoryUtil.eq("${column.name}",

			<#if column.type == "int">
				RandomTestUtil.nextInt()
			<#elseif column.type == "long">
				RandomTestUtil.nextLong()
			<#elseif column.type == "String">
				RandomTestUtil.randomString()
			</#if>

			));
		</#if>

		List<${entity.name}> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		${entity.name} new${entity.name} = add${entity.name}();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, _dynamicQueryClassLoader);

		<#assign column = entity.PKList[0]>

		<#if entity.hasCompoundPK()>
			<#assign propertyName = "id.${column.name}">
		<#else>
			<#assign propertyName = "${column.name}">
		</#if>

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("${propertyName}"));

		Object new${column.methodName} = new${entity.name}.get${column.methodName}();

		dynamicQuery.add(RestrictionsFactoryUtil.in("${propertyName}", new Object[] {new${column.methodName}}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existing${column.methodName} = result.get(0);

		Assert.assertEquals(existing${column.methodName}, new${column.methodName});
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(${entity.name}.class, _dynamicQueryClassLoader);

		<#assign column = entity.PKList[0]>

		<#if entity.hasCompoundPK()>
			<#assign propertyName = "id.${column.name}">
		<#else>
			<#assign propertyName = "${column.name}">
		</#if>

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("${propertyName}"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("${propertyName}", new Object[] {

		<#if column.type == "int">
			RandomTestUtil.nextInt()
		<#elseif column.type == "long">
			RandomTestUtil.nextLong()
		<#elseif column.type == "String">
			RandomTestUtil.randomString()
		</#if>

		}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	<#assign uniqueFinderList = entity.getUniqueFinderList()>

	<#if uniqueFinderList?size != 0>
		@Test
		public void testResetOriginalValues() throws Exception {
			${entity.name} new${entity.name} = add${entity.name}();

			_persistence.clearCache();

			${entity.name} existing${entity.name} = _persistence.findByPrimaryKey(new${entity.name}.getPrimaryKey());

			<#list uniqueFinderList as finder>
				<#assign finderColsList = finder.getColumns()>

				<#list finderColsList as finderCol>
					<#if finderCol.type == "double">
						AssertUtils.assertEquals(existing${entity.name}.get${finderCol.methodName}(), ReflectionTestUtil.<Double>invoke(existing${entity.name}, "getOriginal${finderCol.methodName}", new Class<?>[0]));
					<#elseif finderCol.isPrimitiveType()>
						Assert.assertEquals(existing${entity.name}.get${finderCol.methodName}(), ReflectionTestUtil.invoke(existing${entity.name}, "getOriginal${finderCol.methodName}", new Class<?>[0]));
					<#else>
						Assert.assertTrue(Validator.equals(existing${entity.name}.get${finderCol.methodName}(), ReflectionTestUtil.invoke(existing${entity.name}, "getOriginal${finderCol.methodName}", new Class<?>[0])));
					</#if>
				</#list>
			</#list>
		}
	</#if>

	protected ${entity.name} add${entity.name}() throws Exception {
		<#if entity.hasCompoundPK()>
			${entity.PKClassName} pk = new ${entity.PKClassName}(

			<#list entity.PKList as column>
				<#if column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				<#if column_has_next>
					,
				</#if>
			</#list>

			);
		<#else>
			<#assign column = entity.PKList[0]>

			${column.type} pk =

			<#if column.type == "int">
				RandomTestUtil.nextInt()
			<#elseif column.type == "long">
				RandomTestUtil.nextLong()
			<#elseif column.type == "String">
				RandomTestUtil.randomString()
			</#if>

			;
		</#if>

		${entity.name} ${entity.varName} = _persistence.create(pk);

		<#list entity.regularColList as column>
			<#if !column.primary && ((parentPKColumn == "") || (parentPKColumn.name != column.name))>
				<#if column.type == "Blob">
					String ${column.name}String = RandomTestUtil.randomString();

					byte[] ${column.name}Bytes = ${column.name}String.getBytes(StringPool.UTF8);

					Blob ${column.name}Blob = new OutputBlob(new UnsyncByteArrayInputStream(${column.name}Bytes), ${column.name}Bytes.length);
				</#if>

				${entity.varName}.set${column.methodName}(

				<#if column.type == "boolean">
					RandomTestUtil.randomBoolean()
				<#elseif column.type == "double">
					RandomTestUtil.nextDouble()
				<#elseif column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "Blob">
					${column.name}Blob
				<#elseif column.type == "Date">
					RandomTestUtil.nextDate()
				<#elseif column.type == "Map">
					new HashMap<String, Serializable>()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				);
			</#if>
		</#list>

		_${entity.varNames}.add(_persistence.update(${entity.varName}));

		return ${entity.varName};
	}

	<#if entity.isHierarchicalTree()>
		@Test
		public void testMoveTree() throws Exception {
			long ${scopeColumn.name} = RandomTestUtil.nextLong();

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			${entity.name} child${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			Assert.assertEquals(previousRootLeft${pkColumn.methodName}, root${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(previousRootRight${pkColumn.methodName} + 2, root${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, child${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, child${entity.name}.getRight${pkColumn.methodName}());
		}

		@Test
		public void testMoveTreeFromLeft() throws Exception {
			long ${scopeColumn.name} = RandomTestUtil.nextLong();

			${entity.name} parent${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} child${entity.name} = add${entity.name}(${scopeColumn.name}, parent${entity.name}.get${pkColumn.methodName}());

			parent${entity.name} = _persistence.fetchByPrimaryKey(parent${entity.name}.getPrimaryKey());

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			parent${entity.name}.setParent${pkColumn.methodName}(root${entity.name}.get${pkColumn.methodName}());

			_persistence.update(parent${entity.name});

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			child${entity.name} = _persistence.fetchByPrimaryKey(child${entity.name}.getPrimaryKey());

			Assert.assertEquals(previousRootLeft${pkColumn.methodName} - 4, root${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(previousRootRight${pkColumn.methodName}, root${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, child${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, child${entity.name}.getRight${pkColumn.methodName}());
		}

		@Test
		public void testMoveTreeFromRight() throws Exception {
			long ${scopeColumn.name} = RandomTestUtil.nextLong();

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			${entity.name} parent${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} child${entity.name} = add${entity.name}(${scopeColumn.name}, parent${entity.name}.get${pkColumn.methodName}());

			parent${entity.name} = _persistence.fetchByPrimaryKey(parent${entity.name}.getPrimaryKey());

			parent${entity.name}.setParent${pkColumn.methodName}(root${entity.name}.get${pkColumn.methodName}());

			_persistence.update(parent${entity.name});

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			child${entity.name} = _persistence.fetchByPrimaryKey(child${entity.name}.getPrimaryKey());

			Assert.assertEquals(previousRootLeft${pkColumn.methodName}, root${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(previousRootRight${pkColumn.methodName} + 4, root${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, child${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, child${entity.name}.getRight${pkColumn.methodName}());
		}

		@Test
		public void testMoveTreeIntoTreeFromLeft() throws Exception {
			long ${scopeColumn.name} = RandomTestUtil.nextLong();

			${entity.name} parent${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} parentChild${entity.name} = add${entity.name}(${scopeColumn.name}, parent${entity.name}.get${pkColumn.methodName}());

			parent${entity.name} = _persistence.fetchByPrimaryKey(parent${entity.name}.getPrimaryKey());

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} leftRootChild${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			${entity.name} rightRootChild${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			parent${entity.name}.setParent${pkColumn.methodName}(rightRootChild${entity.name}.get${pkColumn.methodName}());

			_persistence.update(parent${entity.name});

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			leftRootChild${entity.name} = _persistence.fetchByPrimaryKey(leftRootChild${entity.name}.getPrimaryKey());
			rightRootChild${entity.name} = _persistence.fetchByPrimaryKey(rightRootChild${entity.name}.getPrimaryKey());
			parentChild${entity.name} = _persistence.fetchByPrimaryKey(parentChild${entity.name}.getPrimaryKey());

			Assert.assertEquals(previousRootLeft${pkColumn.methodName} - 4, root${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(previousRootRight${pkColumn.methodName}, root${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, leftRootChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 7, leftRootChild${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 3, rightRootChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, rightRootChild${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(rightRootChild${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(rightRootChild${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, parentChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, parentChild${entity.name}.getRight${pkColumn.methodName}());
		}

		@Test
		public void testMoveTreeIntoTreeFromRight() throws Exception {
			long ${scopeColumn.name} = RandomTestUtil.nextLong();

			${entity.name} root${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} leftRootChild${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			${entity.name} rightRootChild${entity.name} = add${entity.name}(${scopeColumn.name}, root${entity.name}.get${pkColumn.methodName}());

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());

			long previousRootLeft${pkColumn.methodName} = root${entity.name}.getLeft${pkColumn.methodName}();
			long previousRootRight${pkColumn.methodName} = root${entity.name}.getRight${pkColumn.methodName}();

			${entity.name} parent${entity.name} = add${entity.name}(${scopeColumn.name}, null);

			${entity.name} parentChild${entity.name} = add${entity.name}(${scopeColumn.name}, parent${entity.name}.get${pkColumn.methodName}());

			parent${entity.name} = _persistence.fetchByPrimaryKey(parent${entity.name}.getPrimaryKey());

			parent${entity.name}.setParent${pkColumn.methodName}(leftRootChild${entity.name}.get${pkColumn.methodName}());

			_persistence.update(parent${entity.name});

			root${entity.name} = _persistence.fetchByPrimaryKey(root${entity.name}.getPrimaryKey());
			leftRootChild${entity.name} = _persistence.fetchByPrimaryKey(leftRootChild${entity.name}.getPrimaryKey());
			rightRootChild${entity.name} = _persistence.fetchByPrimaryKey(rightRootChild${entity.name}.getPrimaryKey());
			parentChild${entity.name} = _persistence.fetchByPrimaryKey(parentChild${entity.name}.getPrimaryKey());

			Assert.assertEquals(previousRootLeft${pkColumn.methodName}, root${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(previousRootRight${pkColumn.methodName} + 4, root${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 1, leftRootChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 3, leftRootChild${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getLeft${pkColumn.methodName}() + 7, rightRootChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(root${entity.name}.getRight${pkColumn.methodName}() - 1, rightRootChild${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(leftRootChild${entity.name}.getLeft${pkColumn.methodName}() + 1, parent${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(leftRootChild${entity.name}.getRight${pkColumn.methodName}() - 1, parent${entity.name}.getRight${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getLeft${pkColumn.methodName}() + 1, parentChild${entity.name}.getLeft${pkColumn.methodName}());
			Assert.assertEquals(parent${entity.name}.getRight${pkColumn.methodName}() - 1, parentChild${entity.name}.getRight${pkColumn.methodName}());
		}

		protected ${entity.name} add${entity.name}(long ${scopeColumn.name}, Long parent${pkColumn.methodName}) throws Exception {
			<#if entity.hasCompoundPK()>
				${entity.PKClassName} pk = new ${entity.PKClassName}(

				<#list entity.PKList as column>
					<#if column.type == "int">
						RandomTestUtil.nextInt()
					<#elseif column.type == "long">
						RandomTestUtil.nextLong()
					<#elseif column.type == "String">
						RandomTestUtil.randomString()
					</#if>

					<#if column_has_next>
						,
					</#if>
				</#list>

				);
			<#else>
				<#assign column = entity.PKList[0]>

				${column.type} pk =

				<#if column.type == "int">
					RandomTestUtil.nextInt()
				<#elseif column.type == "long">
					RandomTestUtil.nextLong()
				<#elseif column.type == "String">
					RandomTestUtil.randomString()
				</#if>

				;
			</#if>

			${entity.name} ${entity.varName} = _persistence.create(pk);

			<#list entity.regularColList as column>
				<#if !column.primary && ((parentPKColumn == "") || (parentPKColumn.name != column.name))>
					<#if column.name ="${scopeColumn.name}">
						${entity.varName}.set${column.methodName}(${scopeColumn.name});
					<#else>
						<#if column.type == "Blob">
							String ${column.name}String = RandomTestUtil.randomString();

							byte[] ${column.name}Bytes = ${column.name}String.getBytes(StringPool.UTF8);

							Blob ${column.name}Blob = new OutputBlob(new UnsyncByteArrayInputStream(${column.name}Bytes), ${column.name}Bytes.length);
						</#if>

						${entity.varName}.set${column.methodName}(

						<#if column.type == "boolean">
							RandomTestUtil.randomBoolean()
						<#elseif column.type == "double">
							RandomTestUtil.nextDouble()
						<#elseif column.type == "int">
							RandomTestUtil.nextInt()
						<#elseif column.type == "long">
							RandomTestUtil.nextLong()
						<#elseif column.type == "Blob">
							${column.name}Blob
						<#elseif column.type == "Date">
							RandomTestUtil.nextDate()
						<#elseif column.type == "String">
							RandomTestUtil.randomString()
						<#elseif column.type == "Map">
							new HashMap();
						</#if>

						);
					</#if>
				</#if>
			</#list>

			if (parent${pkColumn.methodName} != null) {
				${entity.varName}.setParent${pkColumn.methodName}(parent${pkColumn.methodName});
			}

			_persistence.update(${entity.varName});

			return ${entity.varName};
		}
	</#if>

	private List<${entity.name}> _${entity.varNames} = new ArrayList<${entity.name}>();
	private ${entity.name}Persistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}