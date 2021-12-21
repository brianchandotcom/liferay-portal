/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.client.dto.v1_0;

import com.liferay.search.experiences.rest.client.function.UnsafeSupplier;
import com.liferay.search.experiences.rest.client.serdes.v1_0.RangeSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Range implements Cloneable, Serializable {

	public static Range toDTO(String json) {
		return RangeSerDes.toDTO(json);
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setFormat(
		UnsafeSupplier<String, Exception> formatUnsafeSupplier) {

		try {
			format = formatUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String format;

	public Object getGt() {
		return gt;
	}

	public void setGt(Object gt) {
		this.gt = gt;
	}

	public void setGt(UnsafeSupplier<Object, Exception> gtUnsafeSupplier) {
		try {
			gt = gtUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object gt;

	public Object getGte() {
		return gte;
	}

	public void setGte(Object gte) {
		this.gte = gte;
	}

	public void setGte(UnsafeSupplier<Object, Exception> gteUnsafeSupplier) {
		try {
			gte = gteUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object gte;

	public Object getLt() {
		return lt;
	}

	public void setLt(Object lt) {
		this.lt = lt;
	}

	public void setLt(UnsafeSupplier<Object, Exception> ltUnsafeSupplier) {
		try {
			lt = ltUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object lt;

	public Object getLte() {
		return lte;
	}

	public void setLte(Object lte) {
		this.lte = lte;
	}

	public void setLte(UnsafeSupplier<Object, Exception> lteUnsafeSupplier) {
		try {
			lte = lteUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object lte;

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public void setParameterName(
		UnsafeSupplier<String, Exception> parameterNameUnsafeSupplier) {

		try {
			parameterName = parameterNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String parameterName;

	@Override
	public Range clone() throws CloneNotSupportedException {
		return (Range)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Range)) {
			return false;
		}

		Range range = (Range)object;

		return Objects.equals(toString(), range.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RangeSerDes.toJSON(this);
	}

}