@spira_Pagination @Accounts @Pagination @team_FARO @priority_3
Feature: Paginate Account profile in Engagement List
	As a Business User, I should be able to paginate to the second page in engagement list

	Background: [Setup] Navigate to Account page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page

	Scenario: Paginate to second page of engagement list
		Given I click "Schneider and Sons" in the table
		And I click the "Activities" tab
		And I click the "Engagement Score" card tab
		When I set the pagination delta to "5"
		And I go to page "2" in the creation modal
		Then I should be on page "2" in the creation modal
		And I should see an individual named "Maria Rau" in the table