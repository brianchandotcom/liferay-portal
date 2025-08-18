/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class CTSQLTransformerTest extends BaseCTSQLTransformerTestCase {

	@Test
	public void testJoinCount() throws Exception {
		assertQuery(
			"join_count_in.sql", "join_count_out.sql", 0,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		assertQuery(
			"join_count_in.sql", "join_count_out_ct.sql", getCTCollectionId(6),
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testJoinCountAdd() throws Exception {
		long ctCollectionId = getCTCollectionId(1);

		assertQuery(
			"join_count_in.sql", "join_count_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		assertQuery(
			"join_count_in.sql", "join_count_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt6 add"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testJoinCountModify() throws Exception {
		long ctCollectionId = getCTCollectionId(2);

		assertQuery(
			"join_count_in.sql", "join_count_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));

		assertQuery(
			"join_count_in.sql", "join_count_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 modify"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testJoinCountMoved() throws Exception {
		long ctCollectionId = getCTCollectionId(3);

		assertQuery(
			"join_count_in.sql", "join_count_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));

		assertQuery(
			"join_count_in.sql", "join_count_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 moved"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testJoinCountRemove() throws Exception {
		long ctCollectionId = getCTCollectionId(4);

		assertQuery(
			"join_count_in.sql", "join_count_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		assertQuery(
			"join_count_in.sql", "join_count_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt5 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));
	}

	@Test
	public void testJoinSelect() throws Exception {
		assertQuery(
			"join_select_in.sql", "join_select_out.sql", 0,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		assertQuery(
			"join_select_in.sql", "join_select_out_ct.sql",
			getCTCollectionId(6), ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});
	}

	@Test
	public void testJoinSelectAdd() throws Exception {
		long ctCollectionId = getCTCollectionId(1);

		assertQuery(
			"join_select_in.sql", "join_select_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		assertQuery(
			"join_select_in.sql", "join_select_out_ct.sql", ctCollectionId,
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
		long ctCollectionId = getCTCollectionId(2);

		assertQuery(
			"join_select_in.sql", "join_select_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"));

		assertQuery(
			"join_select_in.sql", "join_select_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 modify"),
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
		long ctCollectionId = getCTCollectionId(3);

		assertQuery(
			"join_select_in.sql", "join_select_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"));

		assertQuery(
			"join_select_in.sql", "join_select_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 moved"),
			rs -> {
				Assert.assertEquals(2, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt2 v1", rs.getString("name"));
			});
	}

	@Test
	public void testJoinSelectRemove() throws Exception {
		long ctCollectionId = getCTCollectionId(4);

		assertQuery(
			"join_select_in.sql", "join_select_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		assertQuery(
			"join_select_in.sql", "join_select_out_ct.sql", ctCollectionId,
			ps -> ps.setString(1, "rt5 v1"));
	}

	@Test
	public void testLeftJoin() throws Exception {
		assertQuery(
			"left_join_in.sql", "left_join_out.sql", 0,
			ps -> {
			},
			rs -> Assert.assertEquals(3, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(4, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(5, rs.getLong("mainTableId")));
	}

	@Test
	public void testLeftJoinAdd() throws Exception {
		assertQuery(
			"left_join_in.sql", "left_join_out_ct.sql", getCTCollectionId(1),
			ps -> {
			},
			rs -> Assert.assertEquals(3, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(4, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(5, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(6, rs.getLong("mainTableId")));
	}

	@Test
	public void testLeftJoinModify() throws Exception {
		assertQuery(
			"left_join_in.sql", "left_join_out_ct.sql", getCTCollectionId(2),
			ps -> {
			},
			rs -> Assert.assertEquals(3, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(4, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(5, rs.getLong("mainTableId")));
	}

	@Test
	public void testLeftJoinRemove() throws Exception {
		assertQuery(
			"left_join_in.sql", "left_join_out_ct.sql", getCTCollectionId(4),
			ps -> {
			},
			rs -> Assert.assertEquals(3, rs.getLong("mainTableId")),
			rs -> Assert.assertEquals(5, rs.getLong("mainTableId")));
	}

	@Test
	public void testSelfJoin() throws Exception {
		assertQuery(
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
		long ctCollectionId = getCTCollectionId(1);

		assertQuery(
			"self_join_in.sql", "self_join_out_ct.sql", ctCollectionId,
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
		assertQuery(
			"self_join_in.sql", "self_join_out_ct.sql", getCTCollectionId(2),
			ps -> {
			},
			rs -> {
				Assert.assertEquals(5, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
			});
	}

	@Test
	public void testSelfJoinRemove() throws Exception {
		assertQuery(
			"self_join_in.sql", "self_join_out_ct.sql", getCTCollectionId(4),
			ps -> {
			},
			rs -> {
				Assert.assertEquals(5, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
			});
	}

	@Test
	public void testSimpleCount() throws Exception {
		assertQuery(
			"simple_count_in.sql", "simple_count_out.sql", 0,
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)));

		assertQuery(
			"simple_count_in.sql", "simple_count_out_ct.sql",
			getCTCollectionId(6),
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testSimpleCountAdd() throws Exception {
		assertQuery(
			"simple_count_in.sql", "simple_count_out_ct.sql",
			getCTCollectionId(1),
			ps -> {
			},
			rs -> Assert.assertEquals(6, rs.getLong(1)));
	}

	@Test
	public void testSimpleCountModify() throws Exception {
		assertQuery(
			"simple_count_in.sql", "simple_count_out_ct.sql",
			getCTCollectionId(2),
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testSimpleCountMoved() throws Exception {
		assertQuery(
			"simple_count_in.sql", "simple_count_out_ct.sql",
			getCTCollectionId(3),
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testSimpleCountRemove() throws Exception {
		assertQuery(
			"simple_count_in.sql", "simple_count_out_ct.sql",
			getCTCollectionId(4),
			ps -> {
			},
			rs -> Assert.assertEquals(4, rs.getLong(1)));
	}

	@Test
	public void testSimpleSelect() throws Exception {
		long groupId = 3;

		assertQuery(
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

		assertQuery(
			"simple_select_in.sql", "simple_select_out_ct.sql",
			getCTCollectionId(6), ps -> ps.setLong(1, groupId),
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
		long ctCollectionId = getCTCollectionId(1);
		long groupId = 3;

		assertQuery(
			"simple_select_in.sql", "simple_select_out_ct.sql", ctCollectionId,
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
		long ctCollectionId = getCTCollectionId(2);
		long groupId = 3;

		assertQuery(
			"simple_select_in.sql", "simple_select_out_ct.sql", ctCollectionId,
			ps -> ps.setLong(1, groupId),
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
		long ctCollectionId = getCTCollectionId(3);
		long groupId = 4;

		assertQuery(
			"simple_select_in.sql", "simple_select_out_ct.sql", ctCollectionId,
			ps -> ps.setLong(1, groupId),
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
		long groupId = 3;

		assertQuery(
			"simple_select_in.sql", "simple_select_out_ct.sql",
			getCTCollectionId(4), ps -> ps.setLong(1, groupId),
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
		assertQuery(
			"subquery_count_in.sql", "subquery_count_out.sql", 0,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct.sql",
			getCTCollectionId(6), ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testSubqueryCountAdd() throws Exception {
		long ctCollectionId = getCTCollectionId(1);

		assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt6 add"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testSubqueryCountModify() throws Exception {
		long ctCollectionId = getCTCollectionId(2);

		assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));

		assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 modify"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testSubqueryCountMoved() throws Exception {
		long ctCollectionId = getCTCollectionId(3);

		assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));

		assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 moved"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));
	}

	@Test
	public void testSubqueryCountRemove() throws Exception {
		long ctCollectionId = getCTCollectionId(4);

		assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> Assert.assertEquals(1, rs.getLong(1)));

		assertQuery(
			"subquery_count_in.sql", "subquery_count_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt5 v1"),
			rs -> Assert.assertEquals(0, rs.getLong(1)));
	}

	@Test
	public void testSubquerySelect() throws Exception {
		assertQuery(
			"subquery_select_in.sql", "subquery_select_out.sql", 0,
			ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct.sql",
			getCTCollectionId(6), ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});
	}

	@Test
	public void testSubquerySelectAdd() throws Exception {
		long ctCollectionId = getCTCollectionId(1);

		assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct.sql",
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
		long ctCollectionId = getCTCollectionId(2);

		assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"));

		assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct.sql",
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
		long ctCollectionId = getCTCollectionId(3);

		assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"));

		assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct.sql",
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
		long ctCollectionId = getCTCollectionId(4);

		assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt1 v1"),
			rs -> {
				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals(0, rs.getLong("ctCollectionId"));
				Assert.assertEquals(3, rs.getLong("groupId"));
				Assert.assertEquals("mt1 v1", rs.getString("name"));
			});

		assertQuery(
			"subquery_select_in.sql", "subquery_select_out_ct.sql",
			ctCollectionId, ps -> ps.setString(1, "rt5 v1"));
	}

	@Test
	public void testUnionCount() throws Exception {
		assertQuery(
			"union_select_count_in.sql", "union_select_count_out.sql", 0,
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)),
			rs -> Assert.assertEquals(5, rs.getLong(1)));

		assertQuery(
			"union_select_count_in.sql", "union_select_count_out_ct.sql",
			getCTCollectionId(6),
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)),
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testUnionCountAdd() throws Exception {
		assertQuery(
			"union_select_count_in.sql", "union_select_count_out_ct.sql",
			getCTCollectionId(1),
			ps -> {
			},
			rs -> Assert.assertEquals(6, rs.getLong(1)),
			rs -> Assert.assertEquals(6, rs.getLong(1)));
	}

	@Test
	public void testUnionCountModify() throws Exception {
		assertQuery(
			"union_select_count_in.sql", "union_select_count_out_ct.sql",
			getCTCollectionId(2),
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)),
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testUnionCountMoved() throws Exception {
		assertQuery(
			"union_select_count_in.sql", "union_select_count_out_ct.sql",
			getCTCollectionId(3),
			ps -> {
			},
			rs -> Assert.assertEquals(5, rs.getLong(1)),
			rs -> Assert.assertEquals(5, rs.getLong(1)));
	}

	@Test
	public void testUnionCountRemove() throws Exception {
		assertQuery(
			"union_select_count_in.sql", "union_select_count_out_ct.sql",
			getCTCollectionId(4),
			ps -> {
			},
			rs -> Assert.assertEquals(4, rs.getLong(1)),
			rs -> Assert.assertEquals(4, rs.getLong(1)));
	}

	@Test
	public void testUpdateAndDelete() throws Exception {
		long ctCollectionId7 = createCTEntries(
			7, MainTable.class, null, null, null);

		long ctCollectionId8 = createCTEntries(
			8, MainTable.class, null, null, null);

		db.runSQL(
			"insert into MainTable values (1, " + ctCollectionId7 +
				" , 2, 3, 'temp')");

		assertQuery(
			"select * from MainTable where mainTableId = 1 and " +
				"ctCollectionId = " + ctCollectionId7,
			rs -> {
				Assert.assertTrue(rs.next());

				Assert.assertEquals("temp", rs.getString("name"));

				Assert.assertFalse(rs.next());
			});

		assertUpdate(
			"update_in.sql", "update_out.sql", ctCollectionId7,
			ps -> {
				ps.setLong(1, ctCollectionId8);
				ps.setLong(2, 1);
			});

		assertQuery(
			"select * from MainTable where mainTableId = 1 and " +
				"ctCollectionId = " + ctCollectionId7,
			rs -> Assert.assertFalse(rs.next()));

		assertQuery(
			"select * from MainTable where ctCollectionId = " + ctCollectionId8,
			rs -> {
				Assert.assertTrue(rs.next());

				Assert.assertEquals(1, rs.getLong("mainTableId"));
				Assert.assertEquals("temp", rs.getString("name"));

				Assert.assertFalse(rs.next());
			});

		assertUpdate(
			"delete_in.sql", "delete_out.sql", ctCollectionId8,
			ps -> ps.setLong(1, 1));

		assertQuery(
			"select * from MainTable where mainTableId = 1 and " +
				"ctCollectionId = " + ctCollectionId8,
			rs -> Assert.assertFalse(rs.next()));
	}

}