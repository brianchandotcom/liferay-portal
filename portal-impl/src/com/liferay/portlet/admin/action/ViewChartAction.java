/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.admin.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import java.io.OutputStream;

import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;

/**
 * @author Matthew Kong
 */
public class ViewChartAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			Locale locale = actionRequest.getLocale();

			long maxMemory = ParamUtil.getLong(actionRequest, "maxMemory");
			long totalMemory = ParamUtil.getLong(actionRequest, "totalMemory");
			long usedMemory =
				ParamUtil.getLong(actionRequest, "usedMemory") * 100;

			String type = ParamUtil.getString(actionRequest, "type", "max");

			DefaultValueDataset dataset = null;

			StringBundler sb = new StringBundler(5);

			sb.append(LanguageUtil.get(locale, "used-memory"));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.FORWARD_SLASH);
			sb.append(StringPool.SPACE);

			if (type.equals("total")) {
				dataset = new DefaultValueDataset(usedMemory / totalMemory);

				sb.append(LanguageUtil.get(locale, "total-memory"));
			}
			else {
				dataset = new DefaultValueDataset(usedMemory / maxMemory);

				sb.append(LanguageUtil.get(locale, "maximum-memory"));
			}

			MeterPlot plot = _getMeterPlot(dataset, locale);

			JFreeChart chart = _getChart(sb.toString(), plot);

			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			response.setContentType(ContentTypes.IMAGE_JPEG);

			OutputStream os = response.getOutputStream();

			ChartUtilities.writeChartAsPNG(os, chart, 280, 180);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	private JFreeChart _getChart(String title, MeterPlot plot) {
		JFreeChart chart =
			new JFreeChart(title, new Font(null, Font.PLAIN, 13), plot, true);

		chart.setBackgroundPaint(Color.white);

		chart.removeLegend();

		return chart;
	}

	private MeterPlot _getMeterPlot(
			DefaultValueDataset dataset, Locale locale) {

		MeterPlot plot = new MeterPlot(dataset);

		plot.setDialBackgroundPaint(Color.white);
		plot.setDialShape(DialShape.PIE);
		plot.setDialOutlinePaint(Color.gray);
		plot.setTickLabelFont(new Font(null, Font.PLAIN, 10));
		plot.setTickLabelPaint(Color.darkGray);
		plot.setTickLabelsVisible(true);
		plot.setTickPaint(Color.lightGray);
		plot.setTickSize(5D);
		plot.setMeterAngle(180);
		plot.setNeedlePaint(Color.darkGray);
		plot.setRange(new Range(0.0D, 100D));
		plot.setValueFont(new Font(null, Font.PLAIN, 10));
		plot.setValuePaint(Color.black);
		plot.setUnits("%");

		plot.addInterval(
			new MeterInterval(
				LanguageUtil.get(locale, "normal"), new Range(0.0D, 75D),
				Color.lightGray, new BasicStroke(2.0F),
				new Color(0, 255, 0, 64)));

		plot.addInterval(
			new MeterInterval(
				LanguageUtil.get(locale, "warning"), new Range(75D, 90D),
				Color.lightGray, new BasicStroke(2.0F),
				new Color(255, 255, 0, 64)));

		plot.addInterval(
			new MeterInterval(
				LanguageUtil.get(locale, "critical"), new Range(90D, 100D),
				Color.lightGray, new BasicStroke(2.0F),
				new Color(255, 0, 0, 128)));

		return plot;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

	private static Log _log = LogFactoryUtil.getLog(ViewChartAction.class);

}