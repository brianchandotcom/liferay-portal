package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Daniela Zapata Riesco
 */
public class UpgradePasswordPolicy extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select passwordPolicyId from " +
					"passwordpolicy where uuid_ is null");

			rs = ps.executeQuery();

			while (rs.next()) {
				long passwordPolicyId = rs.getLong("passwordPolicyId");

				runSQL(
					"update PasswordPolicy set uuid_ = '" +
						PortalUUIDUtil.generate() +
							"' where passwordPolicyId = " + passwordPolicyId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}
