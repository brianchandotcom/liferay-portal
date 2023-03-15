@spira_Segments @Segments @team_FARO @priority_3
Feature: Interest details are shown
	As a Business User, I should be able to click into an interest and details are shown

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "everybody" in the table

	Scenario: Click interest shows detail
		Given I click the "Interests" tab
		When I click "mesh" in the table
		Then I should see an individual named "Abram Bauch" in the table