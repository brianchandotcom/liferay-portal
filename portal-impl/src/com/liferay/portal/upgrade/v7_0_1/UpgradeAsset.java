package com.liferay.portal.upgrade.v7_0_1;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UpgradeAsset extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		addAssetEntriesPublishDate();
	}

	protected void addAssetEntriesPublishDate() throws SQLException, IOException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"update AssetEntry set publishDate = createDate where " +
				"publishDate is null");
		}
	}

}