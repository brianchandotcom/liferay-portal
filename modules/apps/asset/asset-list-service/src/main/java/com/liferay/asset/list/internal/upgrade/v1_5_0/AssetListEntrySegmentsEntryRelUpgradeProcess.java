package com.liferay.asset.list.internal.upgrade.v1_5_0;

;
import com.liferay.asset.list.internal.upgrade.v1_5_0.util.AssetListEntrySegmentsEntryRelTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Yurena Cabrera
 */
public class AssetListEntrySegmentsEntryRelUpgradeProcess extends UpgradeProcess {
	@Override
	protected void doUpgrade() throws Exception {
		alter(
			AssetListEntrySegmentsEntryRelTable.class,
			new UpgradeProcess.AlterTableAddColumn("priority", "INTEGER"));
	}
}
