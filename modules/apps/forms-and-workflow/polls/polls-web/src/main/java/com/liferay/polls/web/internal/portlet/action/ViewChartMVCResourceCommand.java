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

package com.liferay.polls.web.internal.portlet.action;

import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.polls.web.internal.portlet.util.PollsUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.OutputStream;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.PieDataset;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PollsPortletKeys.POLLS,
		"javax.portlet.name=" + PollsPortletKeys.POLLS_DISPLAY,
		"mvc.command.name=/polls/view_chart"
	},
	service = MVCResourceCommand.class
)
public class ViewChartMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			long questionId = ParamUtil.getLong(resourceRequest, "questionId");

			String chartType = ParamUtil.getString(
				resourceRequest, "chartType", "pie");
			String chartName = themeDisplay.translate("vote-results");
			String xName = themeDisplay.translate("choice");
			String yName = themeDisplay.translate("votes");

			CategoryDataset categoryDataset = PollsUtil.getVotesDataset(
				questionId);

			JFreeChart jFreeChart = null;

			if (chartType.equals("area")) {
				jFreeChart = ChartFactory.createAreaChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.VERTICAL, true, false, false);
			}
			else if (chartType.equals("horizontal_bar")) {
				jFreeChart = ChartFactory.createBarChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.HORIZONTAL, true, false, false);
			}
			else if (chartType.equals("line")) {
				jFreeChart = ChartFactory.createLineChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.VERTICAL, true, false, false);
			}
			else if (chartType.equals("vertical_bar")) {
				jFreeChart = ChartFactory.createBarChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.VERTICAL, true, false, false);
			}
			else {
				PieDataset pieDataset = DatasetUtilities.createPieDatasetForRow(
					categoryDataset, 0);

				jFreeChart = ChartFactory.createPieChart(
					chartName, pieDataset, true, false, false);
			}

			resourceResponse.setContentType(ContentTypes.IMAGE_JPEG);

			OutputStream outputStream =
				resourceResponse.getPortletOutputStream();

			ChartUtilities.writeChartAsJPEG(outputStream, jFreeChart, 400, 400);
		}
		catch (Exception e) {
			PortletSession portletSession = resourceRequest.getPortletSession();

			PortletContext portletContext = portletSession.getPortletContext();

			PortletRequestDispatcher requestDispatcher =
				portletContext.getRequestDispatcher("/polls/error.jsp");

			requestDispatcher.forward(resourceRequest, resourceResponse);
		}
	}

}