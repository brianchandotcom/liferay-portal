/*******************************************************************************
 * Copyright (c) Dec 5, 2014 Liferay, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Liferay, Inc. - initial API and implementation and/or initial 
 *                    documentation
 ******************************************************************************/

package org.eclipse.equinox.http.servlet.context;

import org.osgi.framework.ServiceReference;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * A customizer that is called by the Http Whiteboard runtime in order to allow
 * customization of context path used for servlets, resources and filters.
 * There are two types of customizations that are allowed.
 * <ol>
 *   <li>Control the default selection filter used when no &quot;osgi.http.whiteboard.context.select&quot;
 *       is specified.</li>
 *   <li>Provide a prefix to the context path &quot;osgi.http.whiteboard.context.path&quot; 
 *       specified by ServletContextHelper registrations.</li>
 * </ol>
 * <p>
 * Registering a customizer results in re-initializing all existing ServletContextHelper registrations.
 * This should not be done often.  Only the highest ranked customizer is used the runtime.
 * </p>
 * <p>
 * <b>Note:</b> This class is part of an interim SPI that is still under 
 * development and expected to change significantly before reaching stability. 
 * It is being made available at this early stage to solicit feedback from pioneering 
 * adopters on the understanding that any code that uses this SPI will almost certainly 
 * be broken (repeatedly) as the SPI evolves.
 * </p>
 * @since 1.2
 */
public abstract class ContextPathCustomizer {
	/**
	 * Returns a service filter that is used to select the default ServletContextHelper when no
	 * selection filter is specified by the whiteboard service.  This method is only
	 * called if the supplied whiteboard service does not provide the 
	 * &quot;osgi.http.whiteboard.context.select&quot; service property.
	 * @param httpWhiteBoardService
	 * @return a service filter that is used to select the default SErvletContextHelper for the
	 * specified whiteboard service.
	 */
	public String getDefaultContextSelectFilter(ServiceReference<?> httpWhiteBoardService) {
		return null;
	}

	/**
	 * Returns a prefix that is prepended to the context path value 
	 * specified by the supplied helper's &quot;osgi.http.whiteboard.context.path&quot;
	 * service property.
	 * @param helper the helper for which the context path will be prepended to
	 * @return the prefix to prepend to the context path 
	 */
	public String getContextPathPrefix(ServiceReference<ServletContextHelper> helper) {
		return null;
	}
}
