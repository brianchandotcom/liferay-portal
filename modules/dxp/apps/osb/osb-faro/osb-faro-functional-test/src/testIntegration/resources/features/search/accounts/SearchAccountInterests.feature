@spira_Search @Search @Accounts @List @team_FARO @priority_3
Feature: Search an Account Profile's Interests
	As an Business User, I should be able to search an Account's Interests

	Background: [Setup] Navigate to an Account
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Search an Account Profile's Interests
		Given I click the "Interests" tab
		When I search for "visionary platforms"
		Then I should see the following ordered rows in the bar graph table:
			| visionary platforms |