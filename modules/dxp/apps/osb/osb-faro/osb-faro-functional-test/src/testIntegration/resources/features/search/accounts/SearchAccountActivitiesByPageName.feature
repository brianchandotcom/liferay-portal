@spira_Search @Search @Accounts @List @team_FARO @priority_3
Feature: Search an Account's Activities
	As an Business User, I should be able to search an Account's Activities by a Page's name

	Background: [Setup] Go to an Account's Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I search for "Hilll, Gottlieb and Dicki"
		* I click "Hilll, Gottlieb and Dicki" in the table

	Scenario: Search an Account's Activities by Page Name
		Given I click the "Activities" tab
		When I search for "target user-centric e-commerce"
		Then I should see an element exists in the table