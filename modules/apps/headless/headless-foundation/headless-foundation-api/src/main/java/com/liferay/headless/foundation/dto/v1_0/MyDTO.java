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

package com.liferay.headless.foundation.dto.v1_0;

import com.liferay.headless.foundation.dto.v1_0.parsing.MyDTOJSONParser;

import java.util.Date;

/**
 * @author Rubén Pulido
 */
public class MyDTO {

	public static MyDTO toMyDto(String json) {
		MyDTOJSONParser jsonParser = new MyDTOJSONParser();

		return jsonParser.parse(json);
	}

	public static MyDTO[] toMyDtos(String json) {
		MyDTOJSONParser jsonParser = new MyDTOJSONParser();

		MyDTO[] myDTOS = jsonParser.parseArray(json);

		if (myDTOS == null) {
			return new MyDTO[0];
		}

		return myDTOS;
	}

	public String getF1() {
		return f1;
	}

	public Boolean getF2() {
		return f2;
	}

	public Long getF3() {
		return f3;
	}

	public Integer getF4() {
		return f4;
	}

	public String[] getF5() {
		return f5;
	}

	public Boolean[] getF6() {
		return f6;
	}

	public Long[] getF7() {
		return f7;
	}

	public Integer[] getF8() {
		return f8;
	}

	public AnotherDTO getF9() {
		return f9;
	}

	public AnotherDTO[] getF10() {
		return f10;
	}

	public MyDTO getF11() {
		return f11;
	}

	public MyDTO[] getF12() {
		return f12;
	}

	public Date getF13() {
		return f13;
	}

	public Date[] getF14() {
		return f14;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public void setF2(Boolean f2) {
		this.f2 = f2;
	}

	public void setF3(Long f3) {
		this.f3 = f3;
	}

	public void setF4(Integer f4) {
		this.f4 = f4;
	}

	public void setF5(String[] f5) {
		this.f5 = f5;
	}

	public void setF6(Boolean[] f6) {
		this.f6 = f6;
	}

	public void setF7(Long[] f7) {
		this.f7 = f7;
	}

	public void setF8(Integer[] f8) {
		this.f8 = f8;
	}

	public void setF9(AnotherDTO f9) {
		this.f9 = f9;
	}

	public void setF10(AnotherDTO[] f10) {
		this.f10 = f10;
	}

	public void setF11(MyDTO f11) {
		this.f11 = f11;
	}

	public void setF12(MyDTO[] f12) {
		this.f12 = f12;
	}

	public void setF13(Date f13) {
		this.f13 = f13;
	}

	public void setF14(Date[] f14) {
		this.f14 = f14;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"f1\": ");

		if (f1 == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(f1);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"f2\": ");

		sb.append(f2);
		sb.append(", ");

		sb.append("\"f3\": ");

		sb.append(f3);
		sb.append(", ");

		sb.append("\"f4\": ");

		sb.append(f4);
		sb.append(", ");

		sb.append("\"f5\": ");

		if (f5 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < f5.length; i++) {
				sb.append("\"");
				sb.append(f5[i]);
				sb.append("\"");

				if ((i + 1) < f5.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"f6\": ");

		if (f6 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < f6.length; i++) {
				sb.append(f6[i]);

				if ((i + 1) < f6.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"f7\": ");

		if (f7 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < f7.length; i++) {
				sb.append(f7[i]);

				if ((i + 1) < f7.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"f8\": ");

		if (f8 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < f8.length; i++) {
				sb.append(f8[i]);

				if ((i + 1) < f8.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"f9\": ");

		sb.append(f9);
		sb.append(", ");

		sb.append("\"f10\": ");

		if (f10 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < f10.length; i++) {
				sb.append(f10[i]);

				if ((i + 1) < f10.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"f11\": ");

		sb.append(f11);
		sb.append(", ");

		sb.append("\"f12\": ");

		if (f12 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < f12.length; i++) {
				sb.append(f12[i]);

				if ((i + 1) < f12.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"f13\": ");

		sb.append(f13);
		sb.append(", ");

		sb.append("\"f14\": ");

		if (f14 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < f14.length; i++) {
				sb.append(f14[i]);

				if ((i + 1) < f14.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	protected String f1;
	protected Boolean f2;
	protected Long f3;
	protected Integer f4;
	protected String[] f5;
	protected Boolean[] f6;
	protected Long[] f7;
	protected Integer[] f8;
	protected AnotherDTO f9;
	protected AnotherDTO[] f10;
	protected MyDTO f11;
	protected MyDTO[] f12;
	protected Date f13;
	protected Date[] f14;

}