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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class FooService {

	public static BarData bar() {
		return new BarData();
	}

	public static String camel(String goodName, String badNAME) {
		return goodName + '*' + badNAME;
	}

	public static int complex(
		List<Long> longs, int[] ints, Map<String, Long> map) {

		return longs.size() + ints.length + map.size();
	}

	public static FooData getFooData(int id) {
		FooData fooData = new FooDataImpl();

		fooData.setId(id);

		if (id == 7) {
			FooDataImpl fooDataImpl = (FooDataImpl)fooData;

			fooDataImpl.setName("James Bond");
			fooDataImpl.setHeight(173);
			fooDataImpl.setValue("licensed");
		}
		else if (id == -13) {
			FooDataImpl fooDataImpl = (FooDataImpl)fooData;

			fooDataImpl.setName("Dr. Evil");
			fooDataImpl.setHeight(59);
			fooDataImpl.setValue("fun");
		}

		return fooData;
	}

	public static FooDataPage getFooDataPage() {
		FooDataAltImpl fooDataAltImpl = new FooDataAltImpl();

		fooDataAltImpl.setArray(9, 5, 7);
		fooDataAltImpl.setHeight(8);
		fooDataAltImpl.setId(2);
		fooDataAltImpl.setName("life");

		return new FooDataPage(fooDataAltImpl, getFooDatas(), 3);
	}

	public static List<FooData> getFooDatas() {
		List<FooData> fooDataList = new ArrayList<FooData>();

		fooDataList.add(getFooData(1));
		fooDataList.add(getFooData(2));
		fooDataList.add(getFooData(3));

		return fooDataList;
	}

	public static FooData[] getFooDatas2() {
		FooData[] fooDataArray = new FooData[3];

		fooDataArray[0] = getFooData(1);
		fooDataArray[1] = getFooData(2);
		fooDataArray[2] = getFooData(3);

		return fooDataArray;
	}

	public static String hello() {
		return "world";
	}

	public static String helloWorld(Integer userId, String worldName) {
		return "Welcome " + userId + " to " + worldName;
	}

	public static String hey(
		Calendar calendar, long[] userIds, List<Locale> locales, Long[] ids) {

		return calendar.get(Calendar.YEAR) + ", " + userIds[0] + '/' +
			userIds.length + ", " + locales.get(0) + '/' + locales.size() +
				", " + ids[0] + '/' + ids.length;
	}

	public static String methodOne(long id, long nameId) {
		return "m-2";
	}

	public static String methodOne(long id, long nameId, String subname) {
		return "m-3";
	}

	public static String methodOne(long id, String name) {
		return "m-1";
	}

	public static String nullLover(String name, int number) {
		if (name == null) {
			return "null!";
		}

		return '[' + name + '|' + number + ']';
	}

	public static String nullReturn() {
		return null;
	}

	public static String search(String name, String... params) {
		return "search " + name + '>' + StringUtil.merge(params);
	}

	public static String srvcctx(ServiceContext serviceContext) {
		Class<?> clazz = serviceContext.getClass();

		return clazz.getName();
	}

	public static ServiceContext srvcctx2(ServiceContext serviceContext) {
		return serviceContext;
	}

	public static String use1(FooDataImpl fooData) {
		return "using #1: " + fooData.toString();
	}

	public static String use2(FooData fooData) {
		return "using #2: " + fooData.toString();
	}

}