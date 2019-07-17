/*******************************************************************************
 * Copyright (c) 2005, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.equinox.metatype.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.parsers.*;
import org.eclipse.equinox.metatype.EquinoxMetaTypeInformation;
import org.eclipse.equinox.metatype.EquinoxMetaTypeService;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.*;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import org.xml.sax.SAXException;

/**
 * Implementation of MetaTypeService
 */
public class MetaTypeServiceImpl implements EquinoxMetaTypeService, SynchronousBundleListener {

	SAXParserFactory _parserFactory;
	private final Map<Long, EquinoxMetaTypeInformation> _mtps = new ConcurrentHashMap<>();

	private final LogService logger;
	private final Map<Bundle, List<Map.Entry<ServiceReference<Object>, Object>>> _metaTypeProviders;

	/**
	 * Constructor of class MetaTypeServiceImpl.
	 */
	public MetaTypeServiceImpl(SAXParserFactory parserFactory, LogService logger, Map<Bundle, List<Map.Entry<ServiceReference<Object>, Object>>> metaTypeProviders) {
		this._parserFactory = parserFactory;
		this.logger = logger;
		_metaTypeProviders = metaTypeProviders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.service.metatype.MetaTypeService#getMetaTypeInformation(org.osgi.framework.Bundle)
	 */
	public EquinoxMetaTypeInformation getMetaTypeInformation(Bundle bundle) {
		return getMetaTypeProvider(bundle);
	}

	/**
	 * Internal Method - to get MetaTypeProvider object.
	 */
	private EquinoxMetaTypeInformation getMetaTypeProvider(final Bundle b) {
		// Avoid synthetic accessor method warnings.
		final LogService loggerTemp = this.logger;
		final Map<Bundle, List<Map.Entry<ServiceReference<Object>, Object>>> metaTypeProviders = _metaTypeProviders;
		Long bID = Long.valueOf(b.getBundleId());

		EquinoxMetaTypeInformation equinoxMetaTypeInformation = _mtps.get(bID);

		if (equinoxMetaTypeInformation != null) {
			return equinoxMetaTypeInformation;
		}

		synchronized (_mtps) {
			if (_mtps.containsKey(bID))
				return _mtps.get(bID);
			EquinoxMetaTypeInformation mti = AccessController.doPrivileged(new PrivilegedAction<EquinoxMetaTypeInformation>() {
				public EquinoxMetaTypeInformation run() {
					MetaTypeInformationImpl impl = null;
					try {
						impl = new MetaTypeInformationImpl(b, newParser(), loggerTemp);
					} catch (Exception e) {
						loggerTemp.log(LogService.LOG_ERROR, NLS.bind(MetaTypeMsg.METADATA_PARSE_ERROR, b.getBundleId(), b.getSymbolicName()), e);
					}
					if (impl == null || !impl._isThereMeta)
						return new MetaTypeProviderTracker(b, loggerTemp, metaTypeProviders);
					return impl;
				}
			});
			_mtps.put(bID, mti);
			return mti;
		}
	}

	SAXParser newParser() throws ParserConfigurationException, SAXException {
		boolean namespaceAware = _parserFactory.isNamespaceAware();
		boolean validating = _parserFactory.isValidating();
		// Always want a non-validating parser.
		_parserFactory.setValidating(false);
		try {
			// If the factory is already namespace aware, we know it can create namespace aware parsers
			// because that was checked in the service tracker.
			if (namespaceAware) {
				return _parserFactory.newSAXParser();
			}
			// If the factory is not already namespace aware, it may or may not be able to create
			// namespace aware parsers.
			_parserFactory.setNamespaceAware(true);
			try {
				return _parserFactory.newSAXParser();
			} catch (Exception e) {
				// Factory cannot create namespace aware parsers. Go with the last resort.
				_parserFactory.setNamespaceAware(false);
				return _parserFactory.newSAXParser();
			}
		} finally {
			// Restore the previous settings in all cases.
			_parserFactory.setNamespaceAware(namespaceAware);
			_parserFactory.setValidating(validating);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleListener#bundleChanged(org.osgi.framework.BundleEvent)
	 */
	public void bundleChanged(BundleEvent event) {

		int type = event.getType();
		Long bID = Long.valueOf(event.getBundle().getBundleId());

		switch (type) {
			case BundleEvent.UPDATED :
			case BundleEvent.UNINSTALLED :
				_mtps.remove(bID);
				break;
			case BundleEvent.INSTALLED :
			case BundleEvent.RESOLVED :
			case BundleEvent.STARTED :
			case BundleEvent.STOPPED :
			case BundleEvent.UNRESOLVED :
			default :
				break;
		}
	}
}
/* @generated */