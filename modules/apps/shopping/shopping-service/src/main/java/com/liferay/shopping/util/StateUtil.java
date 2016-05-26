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

package com.liferay.shopping.util;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 */
public class StateUtil {

	public static final String[] STATE_IDS = new String[] {
		"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI",
		"ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN",
		"MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
		"OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA",
		"WV", "WI", "WY"
	};

	public static final String[] STATE_IDS_ORDERED = new String[] {
		"AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI",
		"IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN",
		"MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH",
		"OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA",
		"WI", "WV", "WY"
	};

	public static final State[] STATES = new State[] {
		new State("AL", "Alabama"), new State("AK", "Alaska"),
		new State("AZ", "Arizona"), new State("AR", "Arkansas"),
		new State("CA", "California"), new State("CO", "Colorado"),
		new State("CT", "Connecticut"), new State("DE", "Delaware"),
		new State("DC", "District of Columbia"), new State("FL", "Florida"),
		new State("GA", "Georgia"), new State("HI", "Hawaii"),
		new State("ID", "Idaho"), new State("IL", "Illinois"),
		new State("IN", "Indiana"), new State("IA", "Iowa"),
		new State("KS", "Kansas"), new State("KY", "Kentucky"),
		new State("LA", "Louisiana"), new State("ME", "Maine"),
		new State("MD", "Maryland"), new State("MA", "Massachusetts"),
		new State("MI", "Michigan"), new State("MN", "Minnesota"),
		new State("MS", "Mississippi"), new State("MO", "Missouri"),
		new State("MT", "Montana"), new State("NE", "Nebraska"),
		new State("NV", "Nevada"), new State("NH", "New Hampshire"),
		new State("NJ", "New Jersey"), new State("NM", "New Mexico"),
		new State("NY", "New York"), new State("NC", "North Carolina"),
		new State("ND", "North Dakota"), new State("OH", "Ohio"),
		new State("OK", "Oklahoma"), new State("OR", "Oregon"),
		new State("PA", "Pennsylvania"), new State("RI", "Rhode Island"),
		new State("SC", "South Carolina"), new State("SD", "South Dakota"),
		new State("TN", "Tennessee"), new State("TX", "Texas"),
		new State("UT", "Utah"), new State("VT", "Vermont"),
		new State("VA", "Virginia"), new State("WA", "Washington"),
		new State("WV", "West Virginia"), new State("WI", "Wisconsin"),
		new State("WY", "Wyoming")
	};

	public static boolean isState(String state) {
		if (Arrays.binarySearch(STATES, state) >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isStateId(String stateId) {
		if (Arrays.binarySearch(STATE_IDS_ORDERED, stateId) >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

}