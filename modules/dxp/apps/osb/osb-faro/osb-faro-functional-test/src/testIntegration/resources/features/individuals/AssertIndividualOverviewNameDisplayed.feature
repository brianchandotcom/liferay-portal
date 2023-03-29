@spira_Individuals @Individuals @team_FARO @priority_4
Feature: Assert individuals name displayed top of page
	As a Business User, I should be able to view individuals name at top of page

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Assert individuals name displayed at top of page
		Given I click the "Known Individuals" tab
		When I click "Abram Bauch" in the table
		Then I should see "Abram Bauch" displayed top of the page