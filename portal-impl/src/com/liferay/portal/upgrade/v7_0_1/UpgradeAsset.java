package com.liferay.portal.upgrade.v7_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.io.IOException;

import java.sql.SQLException;
public class UpgradeAsset extends UpgradeProcess {

	protected void addAssetEntriesPublishDate()
		throws IOException, SQLException {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"update AssetEntry set publishDate = createDate where " +
				"publishDate is null");
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		addAssetEntriesPublishDate();
	}

}