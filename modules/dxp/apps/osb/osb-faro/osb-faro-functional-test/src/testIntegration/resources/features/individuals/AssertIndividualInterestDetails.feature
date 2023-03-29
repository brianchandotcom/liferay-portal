@spira_Individuals @Individuals @Interests @team_FARO @priority_3
Feature: Individuals interest details are shown
	As a Business User, I should be able to click into an individuals interest and details are shown

	Background: [Setup] Navigate to Individuals
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Click interest shows detail
		Given I click the "Interests" tab
		When I click "mesh" in the table
		Then I should see a page named "mesh synergistic schemas" in the table