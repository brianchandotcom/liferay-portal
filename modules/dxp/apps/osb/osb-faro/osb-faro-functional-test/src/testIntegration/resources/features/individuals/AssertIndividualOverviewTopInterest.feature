@spira_Individual @Individual @team_FARO @priority_3
Feature: Assert individuals overview top interest
	As a Business User, I should be able to view individuals overview top interest

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Assert individuals overview top interest
		Given I click the "Known Individuals" tab
		When I click "Abram Bauch" in the table
		Then I should see "mesh" in the card list "Current Interests"