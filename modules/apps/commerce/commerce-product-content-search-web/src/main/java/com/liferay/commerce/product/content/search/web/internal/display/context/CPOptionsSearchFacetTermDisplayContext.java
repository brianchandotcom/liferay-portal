/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.search.web.internal.display.context;

import java.io.Serializable;

import java.util.Locale;

/**
 * @author Andrea Sbarra
 */
public class CPOptionsSearchFacetTermDisplayContext implements Serializable {

	public int getFrequency() {
		return _frequency;
	}

	public Locale getLocale() {
		return _locale;
	}

	public int getPopularity() {
		return _popularity;
	}

	public String getTerm() {
		return _term;
	}

	public boolean isFrequencyVisible() {
		return _frequencyVisible;
	}

	public boolean isSelected() {
		return _selected;
	}

	public void setFrequency(int frequency) {
		_frequency = frequency;
	}

	public void setFrequencyVisible(boolean frequencyVisible) {
		_frequencyVisible = frequencyVisible;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setPopularity(int popularity) {
		_popularity = popularity;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setTerm(String term) {
		_term = term;
	}

	private int _frequency;
	private boolean _frequencyVisible;
	private Locale _locale;
	private int _popularity;
	private boolean _selected;
	private String _term;

}