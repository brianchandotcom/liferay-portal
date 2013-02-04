/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class MembershipPolicyFactoryTest {

	@Test
	public void testRaceCondition() throws InterruptedException {
		Thread hookThread = new Thread() {

			@Override
			public void run() {
				MembershipPolicy membershipPolicy =
					new DefaultMembershipPolicy();

				// Consider this as a hook, that repeatly being deployed and
				// undeployed.

				while (!isInterrupted()) {
					MembershipPolicyFactory.setInstance(membershipPolicy);

					MembershipPolicyFactory.setInstance(null);
				}
			}
		};

		hookThread.start();

		int loopTimes = 10000;

		// Consider this as all possible MembershipPolicy users, that repeatly
		// getting the instance.

		for (int i = 0; i < loopTimes; i++) {
			MembershipPolicy membershipPolicy =
				MembershipPolicyFactory.getInstance();

			if (membershipPolicy == null) {
				Assert.fail("Got a null MembershipPolicy at " + i);
			}
		}

		hookThread.interrupt();
		hookThread.join();
	}

}