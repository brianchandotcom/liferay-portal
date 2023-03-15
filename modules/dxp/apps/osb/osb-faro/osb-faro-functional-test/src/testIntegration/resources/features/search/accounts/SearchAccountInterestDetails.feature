@spira_Search @Search @Accounts @List @team_FARO @priority_3
Feature: Search the Details of an Account's Interest
	As an Business User, I should be able to search the details of an Interest within an Account

	Background: [Setup] Navigate to an Account
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Search the Details of an Account's Interest
		Given I click the "Interests" tab
		And I click "cutting-edge platforms" in the table
		When I search for "Thu Walsh"
		Then I should only see an Individual named "Thu Walsh" in the table