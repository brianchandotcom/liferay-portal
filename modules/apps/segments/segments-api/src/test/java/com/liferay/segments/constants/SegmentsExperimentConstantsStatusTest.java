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

package com.liferay.segments.constants;

import com.liferay.segments.exception.SegmentsExperimentStatusException;

import org.junit.Test;

/**
 * @author David Arques
 */
public class SegmentsExperimentConstantsStatusTest {

	@Test
	public void testValidateTransitionFromCompletedToCompleted()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_COMPLETED,
			SegmentsExperimentConstants.STATUS_COMPLETED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromCompletedToDraft()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_COMPLETED,
			SegmentsExperimentConstants.STATUS_DRAFT);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromCompletedToNoWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_COMPLETED,
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromCompletedToPaused()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_COMPLETED,
			SegmentsExperimentConstants.STATUS_PAUSED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromCompletedToRunning()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_COMPLETED,
			SegmentsExperimentConstants.STATUS_RUNNING);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromCompletedToScheduled()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_COMPLETED,
			SegmentsExperimentConstants.STATUS_SCHEDULED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromCompletedToTerminated()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_COMPLETED,
			SegmentsExperimentConstants.STATUS_TERMINATED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromCompletedToWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_COMPLETED,
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromDraftToCompleted()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_DRAFT,
			SegmentsExperimentConstants.STATUS_COMPLETED);
	}

	@Test
	public void testValidateTransitionFromDraftToDraft()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_DRAFT,
			SegmentsExperimentConstants.STATUS_DRAFT);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromDraftToNoWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_DRAFT,
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromDraftToPaused()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_DRAFT,
			SegmentsExperimentConstants.STATUS_PAUSED);
	}

	@Test
	public void testValidateTransitionFromDraftToRunning()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_DRAFT,
			SegmentsExperimentConstants.STATUS_RUNNING);
	}

	@Test
	public void testValidateTransitionFromDraftToScheduled()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_DRAFT,
			SegmentsExperimentConstants.STATUS_SCHEDULED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromDraftToTerminated()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_DRAFT,
			SegmentsExperimentConstants.STATUS_TERMINATED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromDraftToWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_DRAFT,
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);
	}

	@Test
	public void testValidateTransitionFromNoWinnerToCompleted()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER,
			SegmentsExperimentConstants.STATUS_COMPLETED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromNoWinnerToDraft()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER,
			SegmentsExperimentConstants.STATUS_DRAFT);
	}

	@Test
	public void testValidateTransitionFromNoWinnerToNoWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER,
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromNoWinnerToPaused()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER,
			SegmentsExperimentConstants.STATUS_PAUSED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromNoWinnerToRunning()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER,
			SegmentsExperimentConstants.STATUS_RUNNING);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromNoWinnerToScheduled()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER,
			SegmentsExperimentConstants.STATUS_SCHEDULED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromNoWinnerToTerminated()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER,
			SegmentsExperimentConstants.STATUS_TERMINATED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromNoWinnerToWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER,
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromPausedToCompleted()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_PAUSED,
			SegmentsExperimentConstants.STATUS_COMPLETED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromPausedToDraft()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_PAUSED,
			SegmentsExperimentConstants.STATUS_DRAFT);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromPausedToNoWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_PAUSED,
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER);
	}

	@Test
	public void testValidateTransitionFromPausedToPaused()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_PAUSED,
			SegmentsExperimentConstants.STATUS_PAUSED);
	}

	@Test
	public void testValidateTransitionFromPausedToRunning()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_PAUSED,
			SegmentsExperimentConstants.STATUS_RUNNING);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromPausedToScheduled()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_PAUSED,
			SegmentsExperimentConstants.STATUS_SCHEDULED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromPausedToTerminated()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_PAUSED,
			SegmentsExperimentConstants.STATUS_TERMINATED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromPausedToWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_PAUSED,
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromRunningToCompleted()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_RUNNING,
			SegmentsExperimentConstants.STATUS_COMPLETED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromRunningToDraft()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_RUNNING,
			SegmentsExperimentConstants.STATUS_DRAFT);
	}

	@Test
	public void testValidateTransitionFromRunningToNoWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_RUNNING,
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER);
	}

	@Test
	public void testValidateTransitionFromRunningToPaused()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_RUNNING,
			SegmentsExperimentConstants.STATUS_PAUSED);
	}

	@Test
	public void testValidateTransitionFromRunningToRunning()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_RUNNING,
			SegmentsExperimentConstants.STATUS_RUNNING);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromRunningToScheduled()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_RUNNING,
			SegmentsExperimentConstants.STATUS_SCHEDULED);
	}

	@Test
	public void testValidateTransitionFromRunningToTerminated()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_RUNNING,
			SegmentsExperimentConstants.STATUS_TERMINATED);
	}

	@Test
	public void testValidateTransitionFromRunningToWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_RUNNING,
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromScheduledToCompleted()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_SCHEDULED,
			SegmentsExperimentConstants.STATUS_COMPLETED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromScheduledToDraft()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_SCHEDULED,
			SegmentsExperimentConstants.STATUS_DRAFT);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromScheduledToNoWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_SCHEDULED,
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromScheduledToPaused()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_SCHEDULED,
			SegmentsExperimentConstants.STATUS_PAUSED);
	}

	@Test
	public void testValidateTransitionFromScheduledToRunning()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_SCHEDULED,
			SegmentsExperimentConstants.STATUS_RUNNING);
	}

	@Test
	public void testValidateTransitionFromScheduledToScheduled()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_SCHEDULED,
			SegmentsExperimentConstants.STATUS_SCHEDULED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromScheduledToTerminated()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_SCHEDULED,
			SegmentsExperimentConstants.STATUS_TERMINATED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromScheduledToWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_SCHEDULED,
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromTerminatedToCompleted()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_TERMINATED,
			SegmentsExperimentConstants.STATUS_COMPLETED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromTerminatedToDraft()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_TERMINATED,
			SegmentsExperimentConstants.STATUS_DRAFT);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromTerminatedToNoWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_TERMINATED,
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromTerminatedToPaused()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_TERMINATED,
			SegmentsExperimentConstants.STATUS_PAUSED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromTerminatedToRunning()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_TERMINATED,
			SegmentsExperimentConstants.STATUS_RUNNING);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromTerminatedToScheduled()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_TERMINATED,
			SegmentsExperimentConstants.STATUS_SCHEDULED);
	}

	@Test
	public void testValidateTransitionFromTerminatedToTerminated()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_TERMINATED,
			SegmentsExperimentConstants.STATUS_TERMINATED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromTerminatedToWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_TERMINATED,
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);
	}

	@Test
	public void testValidateTransitionFromWinnerToCompleted()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER,
			SegmentsExperimentConstants.STATUS_COMPLETED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromWinnerToDraft()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER,
			SegmentsExperimentConstants.STATUS_DRAFT);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromWinnerToNoWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER,
			SegmentsExperimentConstants.STATUS_FINISHED_NO_WINNER);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromWinnerToPaused()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER,
			SegmentsExperimentConstants.STATUS_PAUSED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromWinnerToRunning()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER,
			SegmentsExperimentConstants.STATUS_RUNNING);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromWinnerToScheduled()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER,
			SegmentsExperimentConstants.STATUS_SCHEDULED);
	}

	@Test(expected = SegmentsExperimentStatusException.class)
	public void testValidateTransitionFromWinnerToTerminated()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER,
			SegmentsExperimentConstants.STATUS_TERMINATED);
	}

	@Test
	public void testValidateTransitionFromWinnerToWinner()
		throws SegmentsExperimentStatusException {

		SegmentsExperimentConstants.Status.validateTransition(
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER,
			SegmentsExperimentConstants.STATUS_FINISHED_WINNER);
	}

}