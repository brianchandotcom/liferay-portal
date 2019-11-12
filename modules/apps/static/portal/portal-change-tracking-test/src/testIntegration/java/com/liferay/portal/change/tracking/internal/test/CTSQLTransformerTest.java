/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.registry.CTModelRegistration;
import com.liferay.portal.change.tracking.registry.CTModelRegistry;
import com.liferay.portal.change.tracking.sql.CTSQLTransformer;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class CTSQLTransformerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		CTModelRegistry.registerCTModel(
			new CTModelRegistration(
				MainTable.class, "MainTable", "mainTableId", null));

		_createCTEntries(1, MainTable.class, 6L, null, null);
		_createCTEntries(2, MainTable.class, null, 1L, null);
		_createCTEntries(3, MainTable.class, null, 1L, null);
		_createCTEntries(4, MainTable.class, null, null, 4L);
		_createCTEntries(5, MainTable.class, 7L, null, 4L);
		_createCTEntries(6, MainTable.class, null, null, null);

		_db.runSQL(
			StringBundler.concat(
				"create table MainTable (mainTableId LONG not null, ",
				"ctCollectionId LONG not null, companyId LONG, groupId LONG, ",
				"name VARCHAR(20), primary key (mainTableId, ",
				"ctCollectionId));"));

		_db.runSQL("insert into MainTable values (1, 0, 2, 3, 'mt1 v1');");
		_db.runSQL("insert into MainTable values (2, 0, 2, 3, 'mt2 v1');");
		_db.runSQL("insert into MainTable values (3, 0, 2, 3, 'mt3 v1');");
		_db.runSQL("insert into MainTable values (4, 0, 2, 3, 'mt4 v1');");
		_db.runSQL("insert into MainTable values (5, 0, 2, 4, 'mt5 v1');");

		_db.runSQL(
			"insert into MainTable values (6, " + _getCTCollectionId(1) +
				" , 2, 3, 'mt6 add');");

		_db.runSQL(
			"insert into MainTable values (1, " + _getCTCollectionId(2) +
				" , 2, 3, 'mt1 modify');");

		_db.runSQL(
			"insert into MainTable values (1, " + _getCTCollectionId(3) +
				" , 2, 4, 'mt1 moved');");

		_db.runSQL(
			"insert into MainTable values (7, " + _getCTCollectionId(5) +
				" , 2, 3, 'mt7 add');");

		CTModelRegistry.registerCTModel(
			new CTModelRegistration(
				ReferenceTable.class, "ReferenceTable", "referenceTableId",
				null));

		_createCTEntries(1, ReferenceTable.class, 6L, null, null);
		_createCTEntries(2, ReferenceTable.class, null, 1L, null);
		_createCTEntries(3, ReferenceTable.class, null, 1L, null);
		_createCTEntries(4, ReferenceTable.class, null, null, 5L);
		_createCTEntries(5, ReferenceTable.class, null, 1L, 4L);
		_createCTEntries(6, ReferenceTable.class, null, null, null);

		_db.runSQL(
			StringBundler.concat(
				"create table ReferenceTable (referenceTableId LONG not null, ",
				"ctCollectionId LONG not null, mainTableId LONG, name ",
				"VARCHAR(20), primary key (referenceTableId, ",
				"ctCollectionId));"));

		_db.runSQL("insert into ReferenceTable values (1, 0, 1, 'rt1 v1');");
		_db.runSQL("insert into ReferenceTable values (2, 0, 1, 'rt2 v1');");
		_db.runSQL("insert into ReferenceTable values (3, 0, 2, 'rt3 v1');");
		_db.runSQL("insert into ReferenceTable values (4, 0, 2, 'rt4 v1');");
		_db.runSQL("insert into ReferenceTable values (5, 0, 2, 'rt5 v1');");

		_db.runSQL(
			"insert into ReferenceTable values (6, " + _getCTCollectionId(1) +
				" , 1, 'rt6 add');");

		_db.runSQL(
			"insert into ReferenceTable values (1, " + _getCTCollectionId(2) +
				" , 1, 'rt1 modify');");

		_db.runSQL(
			"insert into ReferenceTable values (1, " + _getCTCollectionId(3) +
				" , 2, 'rt1 moved');");

		_db.runSQL(
			"insert into ReferenceTable values (1, " + _getCTCollectionId(5) +
				" , 2, 'rt1 modify2');");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		long userId = TestPropsValues.getUserId();

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(companyId, userId);

		_ctPreferencesLocalService.deleteCTPreferences(ctPreferences);

		_db.runSQL("drop table MainTable;");

		CTModelRegistry.unregisterCTModel("MainTable");

		_db.runSQL("drop table ReferenceTable;");

		CTModelRegistry.unregisterCTModel("ReferenceTable");

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.change.tracking.service.impl." +
						"CTCollectionLocalServiceImpl",
					Level.WARN)) {

			for (CTCollection ctCollection : _ctCollections) {
				_ctCollectionLocalService.deleteCTCollection(ctCollection);
			}
		}
	}

	@Test
	public void testJoinCount() throws Exception {
		_assertQuery(
			"join_count_in.sql", "join_count_out.sql", 0,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		_assertQuery(
			"join_count_in.sql", "join_count_out.sql", _getCTCollectionId(6),
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testJoinCountAdd() throws Exception {
		long ctCollectionId = _getCTCollectionId(1);

		_assertQuery(
			"join_count_in.sql", "join_count_out_ct_add.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		_assertQuery(
			"join_count_in.sql", "join_count_out_ct_add.sql", ctCollectionId,
			ps -> ps.setString(1, "rt6 add"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testJoinCountModify() throws Exception {
		long ctCollectionId = _getCTCollectionId(2);

		_assertQuery(
			"join_count_in.sql", "join_count_out_ct_modify.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));

		_assertQuery(
			"join_count_in.sql", "join_count_out_ct_modify.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 modify"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testJoinCountMoved() throws Exception {
		long ctCollectionId = _getCTCollectionId(3);

		_assertQuery(
			"join_count_in.sql", "join_count_out_ct_modify.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));

		_assertQuery(
			"join_count_in.sql", "join_count_out_ct_modify.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 moved"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testJoinCountRemove() throws Exception {
		long ctCollectionId = _getCTCollectionId(4);

		_assertQuery(
			"join_count_in.sql", "join_count_out_ct_remove.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		_assertQuery(
			"join_count_in.sql", "join_count_out_ct_remove.sql", ctCollectionId,
			ps -> ps.setString(1, "rt5 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));
	}

	@Test
	public void testJoinSelect() throws Exception {
		_assertQuery(
			"join_select_in.sql", "join_select_out.sql", 0,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		_assertQuery(
			"join_select_in.sql", "join_select_out.sql", _getCTCollectionId(6),
			ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});
	}

	@Test
	public void testJoinSelectAdd() throws Exception {
		long ctCollectionId = _getCTCollectionId(1);

		_assertQuery(
			"join_select_in.sql", "join_select_out_ct_add.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		_assertQuery(
			"join_select_in.sql", "join_select_out_ct_add.sql", ctCollectionId,
			ps -> ps.setString(1, "rt6 add"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});
	}

	@Test
	public void testJoinSelectModify() throws Exception {
		long ctCollectionId = _getCTCollectionId(2);

		_assertQuery(
			"join_select_in.sql", "join_select_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"));

		_assertQuery(
			"join_select_in.sql", "join_select_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 modify"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(
					ctCollectionId, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 modify", rs.getString("name"));
			});
	}

	@Test
	public void testJoinSelectMoved() throws Exception {
		long ctCollectionId = _getCTCollectionId(3);

		_assertQuery(
			"join_select_in.sql", "join_select_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"));

		_assertQuery(
			"join_select_in.sql", "join_select_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 moved"),
			rs -> {
				Assert.assertEquals(2, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt2 v1", rs.getString("name"));
			});
	}

	@Test
	public void testJoinSelectRemove() throws Exception {
		long ctCollectionId = _getCTCollectionId(4);

		_assertQuery(
			"join_select_in.sql", "join_select_out_ct_remove.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		_assertQuery(
			"join_select_in.sql", "join_select_out_ct_remove.sql",
			ctCollectionId, ps -> ps.setString(1, "rt5 v1"));
	}

	@Test
	public void testLeftJoin() throws Exception {
		_assertQuery(
			"left_join_in.sql", "left_join_out.sql", 0,
			ps -> {
			},
			rs -> Assert.assertEquals(3, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(4, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(5, rs.getLong("mainTableId")));
	}

	@Test
	public void testLeftJoinAdd() throws Exception {
		_assertQuery(
			"left_join_in.sql", "left_join_out_ct_add.sql",
			_getCTCollectionId(1),
			ps -> {
			},
			rs -> Assert.assertEquals(3, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(4, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(5, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(6, rs.getLong("mainTableId")));
	}

	@Test
	public void testLeftJoinModify() throws Exception {
		_assertQuery(
			"left_join_in.sql", "left_join_out_ct_modify.sql",
			_getCTCollectionId(2),
			ps -> {
			},
			rs -> Assert.assertEquals(3, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(4, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(5, rs.getLong("mainTableId")));
	}

	@Test
	public void testLeftJoinRemove() throws Exception {
		_assertQuery(
			"left_join_in.sql", "left_join_out_ct_remove.sql",
			_getCTCollectionId(4),
			ps -> {
			},
			rs -> Assert.assertEquals(3, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(5, rs.getLong("mainTableId")));
	}

	@Test
	public void testSelfJoin() throws Exception {
		_assertQuery(
			"self_join_in.sql", "self_join_out.sql", 0,
			ps -> {
			},
			rs -> {
				Assert.assertEquals(5, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
			});
	}

	@Test
	public void testSelfJoinAdd() throws Exception {
		long ctCollectionId = _getCTCollectionId(1);

		_assertQuery(
			"self_join_in.sql", "self_join_out_ct_add.sql", ctCollectionId,
			ps -> {
			},
			rs -> {
				Assert.assertEquals(6, rs.getLong("mainTableId"));
				Assert.assertEquals(
					ctCollectionId, rs.getLong("ctCollectionId"));
			});
	}

	@Test
	public void testSelfJoinModify() throws Exception {
		_assertQuery(
			"self_join_in.sql", "self_join_out_ct_modify.sql",
			_getCTCollectionId(2),
			ps -> {
			},
			rs -> {
				Assert.assertEquals(5, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
			});
	}

	@Test
	public void testSelfJoinRemove() throws Exception {
		_assertQuery(
			"self_join_in.sql", "self_join_out_ct_remove.sql",
			_getCTCollectionId(4),
			ps -> {
			},
			rs -> {
				Assert.assertEquals(5, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
			});
	}

	@Test
	public void testSimpleCount() throws Exception {
		_assertQuery(
			"simple_count_in.sql", "simple_count_out.sql", 0,
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)));

		_assertQuery(
			"simple_count_in.sql", "simple_count_out.sql",
			_getCTCollectionId(6),
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testSimpleCountAdd() throws Exception {
		long ctCollectionId = _getCTCollectionId(1);

		_assertQuery(
			"simple_count_in.sql", "simple_count_out_ct_add.sql",
			ctCollectionId,
			ps -> {
			},
			rs -> Assert.assertEquals(6, rs.getLong(1)));
	}

	@Test
	public void testSimpleCountModify() throws Exception {
		long ctCollectionId = _getCTCollectionId(2);

		_assertQuery(
			"simple_count_in.sql", "simple_count_out_ct_modify.sql",
			ctCollectionId,
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testSimpleCountMoved() throws Exception {
		long ctCollectionId = _getCTCollectionId(3);

		_assertQuery(
			"simple_count_in.sql", "simple_count_out_ct_modify.sql",
			ctCollectionId,
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testSimpleCountRemove() throws Exception {
		long ctCollectionId = _getCTCollectionId(4);

		_assertQuery(
			"simple_count_in.sql", "simple_count_out_ct_remove.sql",
			ctCollectionId,
			ps -> {
			},
			rs -> Assert.assertEquals(4, rs.getLong(1)));
	}

	@Test
	public void testSimpleSelect() throws Exception {
		long groupId = 3;

		_assertQuery(
			"simple_select_in.sql", "simple_select_out.sql", 0,
			ps -> ps.setLong(1, groupId),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(2, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt2 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(3, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt3 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(4, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt4 v1", rs.getString("name"));
			});

		_assertQuery(
			"simple_select_in.sql", "simple_select_out.sql",
			_getCTCollectionId(6), ps -> ps.setLong(1, groupId),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(2, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt2 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(3, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt3 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(4, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt4 v1", rs.getString("name"));
			});
	}

	@Test
	public void testSimpleSelectAdd() throws Exception {
		long ctCollectionId = _getCTCollectionId(1);
		long groupId = 3;

		_assertQuery(
			"simple_select_in.sql", "simple_select_out_ct_add.sql",
			ctCollectionId, ps -> ps.setLong(1, groupId),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(2, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt2 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(3, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt3 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(4, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt4 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(6, rs.getLong("mainTableId"));
				Assert.assertEquals(
					ctCollectionId, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt6 add", rs.getString("name"));
			});
	}

	@Test
	public void testSimpleSelectModify() throws Exception {
		long ctCollectionId = _getCTCollectionId(2);
		long groupId = 3;

		_assertQuery(
			"simple_select_in.sql", "simple_select_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setLong(1, groupId),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(
					ctCollectionId, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt1 modify", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(2, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt2 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(3, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt3 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(4, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt4 v1", rs.getString("name"));
			});
	}

	@Test
	public void testSimpleSelectMoved() throws Exception {
		long ctCollectionId = _getCTCollectionId(3);
		long groupId = 4;

		_assertQuery(
			"simple_select_in.sql", "simple_select_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setLong(1, groupId),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(
					ctCollectionId, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt1 moved", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(5, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt5 v1", rs.getString("name"));
			});
	}

	@Test
	public void testSimpleSelectRemove() throws Exception {
		long ctCollectionId = _getCTCollectionId(4);
		long groupId = 3;

		_assertQuery(
			"simple_select_in.sql", "simple_select_out_ct_remove.sql",
			ctCollectionId, ps -> ps.setLong(1, groupId),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(2, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt2 v1", rs.getString("name"));
			},
			rs -> {
				Assert.assertEquals(3, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(groupId, rs.getLong("groupId"));
				Assert.assertEquals("mt3 v1", rs.getString("name"));
			});
	}

	@Test
	public void testSubqueryCount() throws Exception {
		_assertQuery(
			"subquery_count_in.sql", "subquery_count_out.sql", 0,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		_assertQuery(
			"subquery_count_in.sql", "subquery_count_out.sql",
			_getCTCollectionId(6), ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testSubqueryCountAdd() throws Exception {
		long ctCollectionId = _getCTCollectionId(1);

		_assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct_add.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		_assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct_add.sql",
			ctCollectionId, ps -> ps.setString(1, "rt6 add"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testSubqueryCountModify() throws Exception {
		long ctCollectionId = _getCTCollectionId(2);

		_assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));

		_assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 modify"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testSubqueryCountMoved() throws Exception {
		long ctCollectionId = _getCTCollectionId(3);

		_assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));

		_assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 moved"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testSubqueryCountRemove() throws Exception {
		long ctCollectionId = _getCTCollectionId(4);

		_assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct_remove.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		_assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct_remove.sql",
			ctCollectionId, ps -> ps.setString(1, "rt5 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));
	}

	@Test
	public void testSubquerySelect() throws Exception {
		_assertQuery(
			"subquery_select_in.sql", "subquery_select_out.sql", 0,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		_assertQuery(
			"subquery_select_in.sql", "subquery_select_out.sql",
			_getCTCollectionId(6), ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});
	}

	@Test
	public void testSubquerySelectAdd() throws Exception {
		long ctCollectionId = _getCTCollectionId(1);

		_assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct_add.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		_assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct_add.sql",
			ctCollectionId, ps -> ps.setString(1, "rt6 add"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});
	}

	@Test
	public void testSubquerySelectModify() throws Exception {
		long ctCollectionId = _getCTCollectionId(2);

		_assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"));

		_assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 modify"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(
					ctCollectionId, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 modify", rs.getString("name"));
			});
	}

	@Test
	public void testSubquerySelectMoved() throws Exception {
		long ctCollectionId = _getCTCollectionId(3);

		_assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"));

		_assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct_modify.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 moved"),
			rs -> {
				Assert.assertEquals(2, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt2 v1", rs.getString("name"));
			});
	}

	@Test
	public void testSubquerySelectRemove() throws Exception {
		long ctCollectionId = _getCTCollectionId(4);

		_assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct_remove.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		_assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct_remove.sql",
			ctCollectionId, ps -> ps.setString(1, "rt5 v1"));
	}

	@Test
	public void testUnionCount() throws Exception {
		_assertQuery(
			"union_select_count_in.sql", "union_select_count_out.sql", 0,
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)),
			rs -> Assert.assertEquals(5, rs.getLong(1)));

		_assertQuery(
			"union_select_count_in.sql", "union_select_count_out.sql",
			_getCTCollectionId(6),
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)),
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testUnionCountAdd() throws Exception {
		_assertQuery(
			"union_select_count_in.sql", "union_select_count_out_ct_add.sql",
			_getCTCollectionId(1),
			ps -> {
			},
			rs -> Assert.assertEquals(6, rs.getLong(1)),
			rs -> Assert.assertEquals(6, rs.getLong(1)));
	}

	@Test
	public void testUnionCountModify() throws Exception {
		_assertQuery(
			"union_select_count_in.sql", "union_select_count_out_ct_modify.sql",
			_getCTCollectionId(2),
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)),
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testUnionCountMoved() throws Exception {
		_assertQuery(
			"union_select_count_in.sql", "union_select_count_out_ct_modify.sql",
			_getCTCollectionId(3),
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)),
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testUnionCountRemove() throws Exception {
		_assertQuery(
			"union_select_count_in.sql", "union_select_count_out_ct_remove.sql",
			_getCTCollectionId(4),
			ps -> {
			},
			rs -> Assert.assertEquals(4, rs.getLong(1)),
			rs -> Assert.assertEquals(4, rs.getLong(1)));
	}

	@Test
	public void testUpdateAndDelete() throws Exception {
		long ctCollectionId7 = _createCTEntries(
			7, MainTable.class, null, null, null);

		long ctCollectionId8 = _createCTEntries(
			8, MainTable.class, null, null, null);

		_db.runSQL(
			"insert into MainTable values (1, " + ctCollectionId7 +
				" , 2, 3, 'temp');");

		_assertQuery(
			"select * from MainTable where mainTableId = 1 and " +
				"ctCollectionId = " + ctCollectionId7,
			rs -> {
				Assert.assertTrue(rs.next());

				Assert.assertEquals("temp", rs.getString("name"));

				Assert.assertFalse(rs.next());
			});

		_assertUpdate(
			"update_in.sql", "update_out.sql", ctCollectionId7,
			ps -> {
				ps.setLong(1, ctCollectionId8);

				ps.setLong(2, 1);
			});

		_assertQuery(
			"select * from MainTable where mainTableId = 1 and " +
				"ctCollectionId = " + ctCollectionId7,
			rs -> Assert.assertFalse(rs.next()));

		_assertQuery(
			"select * from MainTable where ctCollectionId = " + ctCollectionId8,
			rs -> {
				Assert.assertTrue(rs.next());

				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals("temp", rs.getString("name"));

				Assert.assertFalse(rs.next());
			});

		_assertUpdate(
			"delete_in.sql", "delete_out.sql", ctCollectionId8,
			ps -> ps.setLong(1, 1));

		_assertQuery(
			"select * from MainTable where mainTableId = 1 and " +
				"ctCollectionId = " + ctCollectionId8,
			rs -> Assert.assertFalse(rs.next()));
	}

	private static long _createCTEntries(
			int ctCollectionIndex, Class<?> modelClass, Long addedPK,
			Long modifiedPK, Long removedPK)
		throws Exception {

		CTCollection ctCollection = null;

		if (ctCollectionIndex <= _ctCollections.size()) {
			ctCollection = _ctCollections.get(ctCollectionIndex - 1);
		}

		if (ctCollection == null) {
			long ctCollectionId = _counterLocalService.increment();

			ctCollection = _ctCollectionLocalService.createCTCollection(
				ctCollectionId);

			ctCollection.setName(String.valueOf(ctCollectionId));

			ctCollection = _ctCollectionLocalService.updateCTCollection(
				ctCollection);

			_ctCollections.add(ctCollection);
		}

		if (addedPK != null) {
			_ctEntryLocalService.addCTEntry(
				ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(modelClass),
				_getCTModelProxy(addedPK), TestPropsValues.getUserId(),
				CTConstants.CT_CHANGE_TYPE_ADDITION);
		}

		if (modifiedPK != null) {
			_ctEntryLocalService.addCTEntry(
				ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(modelClass),
				_getCTModelProxy(modifiedPK), TestPropsValues.getUserId(),
				CTConstants.CT_CHANGE_TYPE_MODIFICATION);
		}

		if (removedPK != null) {
			_ctEntryLocalService.addCTEntry(
				ctCollection.getCtCollectionId(),
				_classNameLocalService.getClassNameId(modelClass),
				_getCTModelProxy(removedPK), TestPropsValues.getUserId(),
				CTConstants.CT_CHANGE_TYPE_DELETION);
		}

		return ctCollection.getCtCollectionId();
	}

	private static long _getCTCollectionId(int ctCollectionIndex) {
		if (ctCollectionIndex == 0) {
			return 0;
		}

		CTCollection ctCollection = _ctCollections.get(ctCollectionIndex - 1);

		return ctCollection.getCtCollectionId();
	}

	private static CTModel<?> _getCTModelProxy(long primaryKey) {
		return (CTModel<?>)ProxyUtil.newProxyInstance(
			CTSQLTransformer.class.getClassLoader(),
			new Class<?>[] {CTModel.class},
			(proxy, method, args) -> {
				String methodName = method.getName();

				if (methodName.equals("getMvccVersion")) {
					return 0L;
				}

				if (methodName.equals("getPrimaryKey")) {
					return primaryKey;
				}

				throw new UnsupportedOperationException(method.toString());
			});
	}

	@SafeVarargs
	private final void _assertQuery(
			String inputSQLFile, String expectedOutputSQLFile,
			long ctCollectionId,
			UnsafeConsumer<PreparedStatement, Exception>
				preparedStatementUnsafeConsumer,
			UnsafeConsumer<ResultSet, Exception>... resultSetUnsafeConsumers)
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		long userId = TestPropsValues.getUserId();

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(companyId, userId);

		ctPreferences.setCtCollectionId(ctCollectionId);

		_ctPreferencesLocalService.updateCTPreferences(ctPreferences);

		long originalCompanyId = CompanyThreadLocal.getCompanyId();

		long originalUserId = PrincipalThreadLocal.getUserId();

		CompanyThreadLocal.setCompanyId(companyId);

		PrincipalThreadLocal.setName(userId);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement ps = connection.prepareStatement(
				_getSQL(inputSQLFile, expectedOutputSQLFile, ctCollectionId))) {

			preparedStatementUnsafeConsumer.accept(ps);

			try (ResultSet rs = ps.executeQuery()) {
				for (UnsafeConsumer<ResultSet, Exception> unsafeConsumer :
						resultSetUnsafeConsumers) {

					Assert.assertTrue(rs.next());

					unsafeConsumer.accept(rs);
				}

				Assert.assertFalse(rs.next());
			}
		}
		finally {
			CompanyThreadLocal.setCompanyId(originalCompanyId);

			PrincipalThreadLocal.setName(originalUserId);
		}
	}

	private void _assertQuery(
			String sql, UnsafeConsumer<ResultSet, Exception> unsafeConsumer)
		throws Exception {

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			unsafeConsumer.accept(rs);
		}
	}

	private void _assertUpdate(
			String inputSQLFile, String expectedOutputSQLFile,
			long ctCollectionId,
			UnsafeConsumer<PreparedStatement, Exception>
				preparedStatementUnsafeConsumer)
		throws Exception {

		long companyId = TestPropsValues.getCompanyId();
		long userId = TestPropsValues.getUserId();

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(companyId, userId);

		ctPreferences.setCtCollectionId(ctCollectionId);

		_ctPreferencesLocalService.updateCTPreferences(ctPreferences);

		long originalCompanyId = CompanyThreadLocal.getCompanyId();

		long originalUserId = PrincipalThreadLocal.getUserId();

		CompanyThreadLocal.setCompanyId(companyId);

		PrincipalThreadLocal.setName(userId);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement ps = connection.prepareStatement(
				_getSQL(inputSQLFile, expectedOutputSQLFile, ctCollectionId))) {

			preparedStatementUnsafeConsumer.accept(ps);

			Assert.assertEquals(1, ps.executeUpdate());
		}
		finally {
			CompanyThreadLocal.setCompanyId(originalCompanyId);

			PrincipalThreadLocal.setName(originalUserId);
		}
	}

	private String _getModifiedAndRemovedModelClassPKSQL(
		long ctCollectionId, Class<?> modelClass) {

		List<CTEntry> ctEntries = _ctEntryLocalService.getCTEntries(
			ctCollectionId, _classNameLocalService.getClassNameId(modelClass));

		StringBundler sb = new StringBundler();

		for (CTEntry ctEntry : ctEntries) {
			if (ctEntry.getChangeType() !=
					CTConstants.CT_CHANGE_TYPE_ADDITION) {

				sb.append(ctEntry.getModelClassPK());
				sb.append(",");
			}
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private String _getSQL(
			String inputSQLFile, String expectedOutputSQLFile,
			long ctCollectionId)
		throws Exception {

		String inputSQL = StreamUtil.toString(
			CTSQLTransformerTest.class.getResourceAsStream(
				"dependencies/" + inputSQLFile));

		String expectedOutputSQL = _normalizeSQL(
			StreamUtil.toString(
				CTSQLTransformerTest.class.getResourceAsStream(
					"dependencies/" + expectedOutputSQLFile)));

		Map<String, String> replaceMap = HashMapBuilder.put(
			"CT_COLLECTION_ID", String.valueOf(ctCollectionId)
		).put(
			"MAIN_TABLE_CT_ENTRY_MODEL_CLASS_PKS",
			_getModifiedAndRemovedModelClassPKSQL(
				ctCollectionId, MainTable.class)
		).put(
			"REFERENCE_TABLE_CT_ENTRY_MODEL_CLASS_PKS",
			_getModifiedAndRemovedModelClassPKSQL(
				ctCollectionId, ReferenceTable.class)
		).build();

		expectedOutputSQL = StringUtil.replace(
			expectedOutputSQL, "[$", "$]", replaceMap);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.change.tracking.internal." +
						"CTSQLContextFactoryImpl",
					Level.WARN)) {

			String newSQL = _ctSQLTransformer.transform(inputSQL);

			Assert.assertEquals(expectedOutputSQL, newSQL);

			List<LoggingEvent> loggingEvents =
				captureAppender.getLoggingEvents();

			if (expectedOutputSQLFile.contains("_ct_")) {
				Assert.assertFalse(newSQL, loggingEvents.isEmpty());
			}
			else {
				Assert.assertTrue(newSQL, loggingEvents.isEmpty());
			}

			return newSQL;
		}
	}

	private String _normalizeSQL(String sql) {
		return StringUtil.replace(
			sql.trim(), new String[] {"\n", "   ", "  ", "( ", " )"},
			new String[] {" ", " ", " ", "(", ")"});
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CounterLocalService _counterLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	private static final List<CTCollection> _ctCollections = new ArrayList<>();

	@Inject
	private static CTEntryLocalService _ctEntryLocalService;

	@Inject
	private static CTPreferencesLocalService _ctPreferencesLocalService;

	@Inject
	private static CTSQLTransformer _ctSQLTransformer;

	private static DB _db;

	private static class MainTable {
	}

	private static class ReferenceTable {
	}

}