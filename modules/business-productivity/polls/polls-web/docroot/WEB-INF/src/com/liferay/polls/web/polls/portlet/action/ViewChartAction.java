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

package com.liferay.polls.web.polls.portlet.action;

import com.liferay.polls.web.util.PollsUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.PortletAction;

import java.io.OutputStream;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.PieDataset;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewChartAction extends PortletAction {

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse renderResponse)
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

			JFreeChart jFreeChat = null;

			if (chartType.equals("area")) {
				jFreeChat = ChartFactory.createAreaChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.VERTICAL, true, false, false);
			}
			else if (chartType.equals("horizontal_bar")) {
				jFreeChat = ChartFactory.createBarChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.HORIZONTAL, true, false, false);
			}
			else if (chartType.equals("line")) {
				jFreeChat = ChartFactory.createLineChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.VERTICAL, true, false, false);
			}
			else if (chartType.equals("vertical_bar")) {
				jFreeChat = ChartFactory.createBarChart(
					chartName, xName, yName, categoryDataset,
					PlotOrientation.VERTICAL, true, false, false);
			}
			else {
				PieDataset pieDataset = DatasetUtilities.createPieDatasetForRow(
					categoryDataset, 0);

				jFreeChat = ChartFactory.createPieChart(
					chartName, pieDataset, true, false, false);
			}

			renderResponse.setContentType(ContentTypes.IMAGE_JPEG);

			OutputStream outputStream = renderResponse.getPortletOutputStream();

			ChartUtilities.writeChartAsJPEG(outputStream, jFreeChat, 400, 400);
		}
		catch (Exception e) {
			PortletSession portletSession = resourceRequest.getPortletSession();

			PortletContext portletContext = portletSession.getPortletContext();

			PortletRequestDispatcher requestDispatcher =
				portletContext.getRequestDispatcher(
					"/html/portlet/polls/error.jsp");

			requestDispatcher.forward(resourceRequest, renderResponse);
		}
	}

}