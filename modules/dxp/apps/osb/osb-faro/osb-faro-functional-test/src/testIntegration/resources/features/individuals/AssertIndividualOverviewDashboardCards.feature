@spira_Individuals @Individuals @Dashboard @team_FARO @priority_4
Feature: Assert the Individuals Overview Dashboard Cards
	As an Business User, I should be able to assert the cards on the Overview tab of the Individual's Dashboard page

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"

	Scenario: Assert the Individuals Overview Dashboard
		Given I should see the "Sites" page
		When I go to the "Individuals" page
		Then I should see the Individual Overview Dashboard Cards