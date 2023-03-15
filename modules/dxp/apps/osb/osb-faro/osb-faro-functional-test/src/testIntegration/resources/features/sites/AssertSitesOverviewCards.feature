@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert the Sites Overview Cards
	As a Business User, I should be able to assert the cards on the overview tab of a Site's page

	Background: [Setup] Navigate to Sites page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert the Sites Overview Cards
		Given I go to the "Sites" page
		Then I should see the Sites Overview Cards with their details