package com.liferay.portal.util;

import java.io.Serializable;

public class EscapableObject<T> implements Serializable {

	public EscapableObject(T raw) {
		this(raw, true);
	}

	public EscapableObject(T raw, boolean escape) {
		_raw = raw;
		_escape = escape;
	}

	protected String escape(T t) {
		return String.valueOf(t);
	}

	public String getEscaped() {
		if (_cachedEscape == null) {
			if (_escape) {
				_cachedEscape = escape(_raw);
			}
			else {
				_cachedEscape = String.valueOf(_raw);
			}
		}

		return _cachedEscape;
	}

	public T getRaw() {
		return _raw;
	}

	public String toString() {
		return _raw.toString();
	}

	protected final boolean _escape;
	protected String _cachedEscape;
	protected final T _raw;

}