@spira_Individuals @Individuals @team_FARO @priority_5
Feature: Assert Individuals Overview time filter Custom Range
	As a Business User, I should be able to change individual overview time filter to Custom Range

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Change Individual time filter Custom Range
		Given I click the "Last 30 days" dropdown in the "Active Individuals" card
		When I click the "Custom Range" button
		And I set start date "July 1 2020" and end date "July 13 2020"
		Then I should see time filter "Jul 1, 2020 - Jul 13, 2020" in "Active Individuals" card
		And I should see granularity starts "Jul 1" and ends "Jul 13" in "Active Individuals" card