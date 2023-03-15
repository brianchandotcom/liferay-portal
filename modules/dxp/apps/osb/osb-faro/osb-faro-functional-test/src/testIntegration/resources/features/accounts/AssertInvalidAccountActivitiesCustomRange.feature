@spira_Accounts @Accounts @team_FARO @priority_2
Feature: Assert that a User can't set a Custom Range past 365 days max for Account Activities
	As an Business User, I should not be able to set a Custom Range past the 365 days max for Individuals

	Background: [Setup] Navigate to Accounts
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Assert Custom Range past 365 days max is invalid
		Given I click the "Activities" tab
		And I click the "Last 30 days" dropdown in the card
		When I click the "Custom Range" button
		And I set start date "August 1 2019" and end date "August 2 2020"
		Then I should see an error saying the range exceeds the maximum range