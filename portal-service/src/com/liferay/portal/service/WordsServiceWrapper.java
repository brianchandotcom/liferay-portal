/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link WordsService}.
 *
 * @author Brian Wing Shun Chan
 * @see WordsService
 * @generated
 */
@ProviderType
public class WordsServiceWrapper implements WordsService,
	ServiceWrapper<WordsService> {
	public WordsServiceWrapper(WordsService wordsService) {
		_wordsService = wordsService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _wordsService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_wordsService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.util.List<java.lang.String> checkSpelling(java.lang.String text) {
		return _wordsService.checkSpelling(text);
	}

	@Override
	public java.util.List<java.lang.String> getSuggestions(
		java.lang.String word) {
		return _wordsService.getSuggestions(word);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public WordsService getWrappedWordsService() {
		return _wordsService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedWordsService(WordsService wordsService) {
		_wordsService = wordsService;
	}

	@Override
	public WordsService getWrappedService() {
		return _wordsService;
	}

	@Override
	public void setWrappedService(WordsService wordsService) {
		_wordsService = wordsService;
	}

	private WordsService _wordsService;
}