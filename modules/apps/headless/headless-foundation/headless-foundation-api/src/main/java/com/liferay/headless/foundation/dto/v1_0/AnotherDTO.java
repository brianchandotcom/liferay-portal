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

import com.liferay.headless.foundation.dto.v1_0.parsing.AnotherDTOJSONParser;

/**
 * @author Rubén Pulido
 */
public class AnotherDTO {

	public static AnotherDTO toAnotherDto(String json) {
		AnotherDTOJSONParser jsonParser = new AnotherDTOJSONParser();

		return jsonParser.parse(json);
	}

	public static AnotherDTO[] toAnotherDtos(String json) {
		AnotherDTOJSONParser jsonParser = new AnotherDTOJSONParser();

		AnotherDTO[] anotherDTOS = jsonParser.parseArray(json);

		if (anotherDTOS == null) {
			return new AnotherDTO[0];
		}

		return anotherDTOS;
	}

	public String getA1() {
		return a1;
	}

	public Boolean getA2() {
		return a2;
	}

	public Long getA3() {
		return a3;
	}

	public Integer getA4() {
		return a4;
	}

	public String[] getA5() {
		return a5;
	}

	public Boolean[] getA6() {
		return a6;
	}

	public Long[] getA7() {
		return a7;
	}

	public Integer[] getA8() {
		return a8;
	}

	public MyDTO getA9() {
		return a9;
	}

	public MyDTO[] getA10() {
		return a10;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public void setA2(Boolean a2) {
		this.a2 = a2;
	}

	public void setA3(Long a3) {
		this.a3 = a3;
	}

	public void setA4(Integer a4) {
		this.a4 = a4;
	}

	public void setA5(String[] a5) {
		this.a5 = a5;
	}

	public void setA6(Boolean[] a6) {
		this.a6 = a6;
	}

	public void setA7(Long[] a7) {
		this.a7 = a7;
	}

	public void setA8(Integer[] a8) {
		this.a8 = a8;
	}

	public void setA9(MyDTO a9) {
		this.a9 = a9;
	}

	public void setA10(MyDTO[] a10) {
		this.a10 = a10;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{");

		sb.append("\"a1\": ");

		if (a1 == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(a1);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"a2\": ");

		sb.append(a2);
		sb.append(", ");

		sb.append("\"a3\": ");

		sb.append(a3);
		sb.append(", ");

		sb.append("\"a4\": ");

		sb.append(a4);
		sb.append(", ");

		sb.append("\"a5\": ");

		if (a5 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < a5.length; i++) {
				sb.append("\"");
				sb.append(a5[i]);
				sb.append("\"");

				if ((i + 1) < a5.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"a6\": ");

		if (a6 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < a6.length; i++) {
				sb.append(a6[i]);

				if ((i + 1) < a6.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"a7\": ");

		if (a7 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < a7.length; i++) {
				sb.append(a7[i]);

				if ((i + 1) < a7.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"a8\": ");

		if (a8 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < a8.length; i++) {
				sb.append(a8[i]);

				if ((i + 1) < a8.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"a9\": ");

		sb.append(a9);
		sb.append(", ");

		sb.append("\"a10\": ");

		if (a10 == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < a10.length; i++) {
				sb.append(a10[i]);

				if ((i + 1) < a10.length) {
					sb.append(",");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	protected String a1;
	protected Boolean a2;
	protected Long a3;
	protected Integer a4;
	protected String[] a5;
	protected Boolean[] a6;
	protected Long[] a7;
	protected Integer[] a8;
	protected MyDTO a9;
	protected MyDTO[] a10;

}