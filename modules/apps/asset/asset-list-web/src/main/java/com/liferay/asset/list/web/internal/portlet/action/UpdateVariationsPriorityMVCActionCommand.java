package com.liferay.asset.list.web.internal.portlet.action;

import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalServiceUtil;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetListPortletKeys.ASSET_LIST,
		"mvc.command.name=/asset_list/update_variations_priority"
	},
	service = MVCResourceCommand.class
)
public class UpdateVariationsPriorityMVCActionCommand
	 extends BaseMVCActionCommand {


	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] variationsPriority = ParamUtil.getLongValues(
			actionRequest, "variationsPriority");

		for (int priority = 0; priority < variationsPriority.length; priority++){
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
				AssetListEntrySegmentsEntryRelLocalServiceUtil.
					getAssetListEntrySegmentsEntryRel(
						variationsPriority[priority]);
			assetListEntrySegmentsEntryRel.setPriority(priority);

			AssetListEntrySegmentsEntryRelLocalServiceUtil.updateAssetListEntrySegmentsEntryRel(assetListEntrySegmentsEntryRel);
		}

	}
}
