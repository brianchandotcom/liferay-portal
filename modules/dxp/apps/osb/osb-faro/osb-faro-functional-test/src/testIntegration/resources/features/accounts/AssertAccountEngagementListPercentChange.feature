@component_Accounts @team_FARO @priority_3
Feature: Account activities engagement list percent change
	As a Business User, I should be able to view engagement list percent change

	Background: [Setup] Navigate to Accounts page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Schneider and Sons" in the table

	Scenario: View engagement list percent change
		Given I click the "Activities" tab
		When I click the "Engagement Score" card tab
		Then I should see a percent change