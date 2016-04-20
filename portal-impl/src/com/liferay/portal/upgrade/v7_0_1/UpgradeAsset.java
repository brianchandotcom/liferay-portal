package com.liferay.portal.upgrade.v7_0_1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

public class UpgradeAsset extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addAssetPublishDate();
	}

	private void addAssetPublishDate() throws SQLException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {

			StringBundler sb = new StringBundler();

			sb.append("select AssetEntry.entryId, AssetEntry.createDate ");
			sb.append("from AssetEntry where publishDate is null ");

			try (PreparedStatement ps1 = connection.prepareStatement(
					sb.toString())) {

				try (PreparedStatement ps2 =
						AutoBatchPreparedStatementUtil.concurrentAutoBatch(
							connection,
							"update AssetEntry set publishDate = ? where " +
								"entryId = ?");
					ResultSet rs = ps1.executeQuery()) {

					while (rs.next()) {
						ps2.setTimestamp(1, rs.getTimestamp(2));
						ps2.setLong(2, rs.getLong(1));
						ps2.addBatch();
					}

					ps2.executeBatch();
				}
			}
		}
	}

}
