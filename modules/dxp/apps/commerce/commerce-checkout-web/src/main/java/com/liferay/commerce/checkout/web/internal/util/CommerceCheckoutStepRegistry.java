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

package com.liferay.commerce.checkout.web.internal.util;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = CommerceCheckoutStepRegistry.class)
public class CommerceCheckoutStepRegistry {

	public String getFirstMVCRenderCommandName() {
		CheckoutStepMVCRenderCommand checkoutStepMVCRenderCommand =
			_checkoutStepMVCRenderCommands.first();

		return checkoutStepMVCRenderCommand.name;
	}

	public String getNextMVCRenderCommandName(String mvcRenderCommandName) {
		Iterator<CheckoutStepMVCRenderCommand> iterator =
			_checkoutStepMVCRenderCommands.iterator();

		while (iterator.hasNext()) {
			CheckoutStepMVCRenderCommand checkoutStepMVCRenderCommand =
				iterator.next();

			if (checkoutStepMVCRenderCommand.name.equals(
					mvcRenderCommandName) &&
				iterator.hasNext()) {

				checkoutStepMVCRenderCommand = iterator.next();

				return checkoutStepMVCRenderCommand.name;
			}
		}

		return null;
	}

	public String getPreviousMVCRenderCommandName(String mvcRenderCommandName) {
		String previousMVCRenderCommandName = null;

		for (CheckoutStepMVCRenderCommand checkoutStepMVCRenderCommand :
				_checkoutStepMVCRenderCommands) {

			if (checkoutStepMVCRenderCommand.name.equals(
					mvcRenderCommandName)) {

				return previousMVCRenderCommandName;
			}

			previousMVCRenderCommandName = checkoutStepMVCRenderCommand.name;
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		StringBundler sb = new StringBundler();

		sb.append("(&(commerce.checkout.step.order=*)(javax.portlet.name=");
		sb.append(CommercePortletKeys.COMMERCE_CHECKOUT);
		sb.append(")(mvc.command.name=*)(objectClass=");
		sb.append(MVCRenderCommand.class.getName());
		sb.append("))");

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, sb.toString(),
			new CheckoutStepMVCRenderCommandServiceTrackerCustomized());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private final SortedSet<CheckoutStepMVCRenderCommand>
		_checkoutStepMVCRenderCommands = new ConcurrentSkipListSet<>();
	private ServiceTracker<MVCRenderCommand, CheckoutStepMVCRenderCommand>
		_serviceTracker;

	private static class CheckoutStepMVCRenderCommand
		implements Comparable<CheckoutStepMVCRenderCommand> {

		public CheckoutStepMVCRenderCommand(String name, int order) {
			this.name = name;
			this.order = order;
		}

		@Override
		public int compareTo(
			CheckoutStepMVCRenderCommand checkoutStepMVCRenderCommand) {

			return Integer.compare(order, checkoutStepMVCRenderCommand.order);
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof CheckoutStepMVCRenderCommand)) {
				return false;
			}

			CheckoutStepMVCRenderCommand checkoutStepMVCRenderCommand =
				(CheckoutStepMVCRenderCommand)obj;

			return name.equals(checkoutStepMVCRenderCommand.name);
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		public final String name;
		public final int order;

	}

	private class CheckoutStepMVCRenderCommandServiceTrackerCustomized
		implements ServiceTrackerCustomizer
			<MVCRenderCommand, CheckoutStepMVCRenderCommand> {

		@Override
		public CheckoutStepMVCRenderCommand addingService(
			ServiceReference<MVCRenderCommand> serviceReference) {

			String name = (String)serviceReference.getProperty(
				"mvc.command.name");
			int order = (Integer)serviceReference.getProperty(
				"commerce.checkout.step.order");

			CheckoutStepMVCRenderCommand checkoutStepMVCRenderCommand =
				new CheckoutStepMVCRenderCommand(name, order);

			_checkoutStepMVCRenderCommands.add(checkoutStepMVCRenderCommand);

			return checkoutStepMVCRenderCommand;
		}

		@Override
		public void modifiedService(
			ServiceReference<MVCRenderCommand> serviceReference,
			CheckoutStepMVCRenderCommand checkoutStepMVCRenderCommand) {

			removedService(serviceReference, checkoutStepMVCRenderCommand);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<MVCRenderCommand> serviceReference,
			CheckoutStepMVCRenderCommand checkoutStepMVCRenderCommand) {

			_checkoutStepMVCRenderCommands.remove(checkoutStepMVCRenderCommand);
		}

	}

}