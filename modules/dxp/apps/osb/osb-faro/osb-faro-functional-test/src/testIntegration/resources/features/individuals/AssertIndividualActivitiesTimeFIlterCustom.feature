@spira_Individuals @Individuals @team_FARO @priority_5
Feature: Assert Individual Activities Custom Range
	As a Business User, I should be able to set Custom Range in Individual Activities

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Individual Activities Custom Range
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		When I click the "Last 30 days" dropdown in the "Individual Activities" card
		And I click the "Custom Range" button
		And I set start date "July 1 2020" and end date "July 13 2020"
		Then I should see time filter "Jul 1, 2020 - Jul 13, 2020" in "Individual Activities" card
		And I should see granularity starts "Jul 1" and ends "Jul 13" in "Individual Activities" card