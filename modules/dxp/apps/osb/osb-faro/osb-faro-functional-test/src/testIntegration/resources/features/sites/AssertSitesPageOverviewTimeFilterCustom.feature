@spira_Sites @Sites @team_FARO @priority_5
Feature: Assert Sites Page Overview time filter to Custom Filter
	As a Business User, I should be able to change to Custom time filter

	Background: [Setup] Navigate to Page Overview
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click the "Pages" tab
		* I click "mesh synergistic schemas" in the table

	Scenario: Assert Page Overview Custom Filter
		Given I click the "Last 30 days" dropdown in the "Visitors Behavior" card
		When I click the "Custom Range" button
		And I set start date "July 1 2020" and end date "July 13 2020"
		Then I should see time filter "Jul 1, 2020 - Jul 13, 2020" in "Visitors Behavior" card
		And I should see granularity starts "Jul 1" and ends "Jul 13" in "Visitors Behavior" card